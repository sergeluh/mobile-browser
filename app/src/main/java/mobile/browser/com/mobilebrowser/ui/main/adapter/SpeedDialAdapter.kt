package mobile.browser.com.mobilebrowser.ui.main.adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.speed_dial_list_item.view.*
import mobile.browser.com.mobilebrowser.R
import mobile.browser.com.mobilebrowser.api.ApiInterface
import mobile.browser.com.mobilebrowser.ui.main.model.SpeedDialItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class SpeedDialAdapter(private val items: MutableList<SpeedDialItem>, private val clickListener: (String) -> Unit): RecyclerView.Adapter<SpeedDialAdapter.SpeedDialViewHolder>(), KoinComponent{

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SpeedDialViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.speed_dial_list_item, p0, false)
        return SpeedDialViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: SpeedDialViewHolder, p1: Int) {
        p0.bind(items[p1], clickListener)
    }

    class SpeedDialViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(item: SpeedDialItem, clickListener: (String) -> Unit){
            doAsync {
                var iStream: InputStream? = null
                try {
                    var url = URL("https", item.url, "/favicon.ico")
                    Log.d("MYURL", "${url.ref}, $url")
                    var connection = url.openConnection()
                    connection.connect()
                    iStream = connection.getInputStream()
                    val bitmap = BitmapFactory.decodeStream(iStream)

                    uiThread {
                        itemView.speed_dial_list_item_image.setImageBitmap(bitmap)
                    }
                    iStream.close()

                    url = URL("https://${item.url}")
                    connection = url.openConnection()
                    iStream = connection.getInputStream()
                    val reader = BufferedReader(InputStreamReader(iStream, "UTF-8"))
                    val sb = StringBuilder()
                    var line = reader.readLine()
                    while (line != null){
                        sb.append(line)
                        line = reader.readLine()
                    }
                    val html = sb.toString()
                    if (html.contains("bing", true)) {
                        Log.d("MYURL", html)
                    }
                    val title = html.substring(html.indexOf("<title>") + 7, html.indexOf("</title>"))
                    uiThread {
                        itemView.title.text = title
                    }

                }catch (e: Exception){
                    Log.e("MYURL", e.message)
                    uiThread {
                        itemView.speed_dial_list_item_image.setImageResource(item.icon)
                    }
                }finally {
                    iStream?.close()
                }
            }
            itemView.setOnClickListener {
                clickListener(item.url)
            }
        }
    }
}