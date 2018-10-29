package mobile.browser.com.mobilebrowser.ui.main.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.speed_dial_list_item.view.*
import mobile.browser.com.mobilebrowser.R
import mobile.browser.com.mobilebrowser.ui.main.model.SpeedDialItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.koin.standalone.KoinComponent
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection

class SpeedDialAdapter(private val items: MutableList<SpeedDialItem>, private val clickListener: (String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), KoinComponent {

    companion object {
        private val iconCache = mutableMapOf<String, Bitmap?>()
        private val titleCache = mutableMapOf<String, String?>()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.speed_dial_list_item, p0, false)
        return if (p1 == 0) AddButtonViewHolder(view) else SpeedDialViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (getItemViewType(p1) == 0){
            (p0 as AddButtonViewHolder).bind(items[p1])
        }else {
            (p0 as SpeedDialViewHolder).bind(items[p1], clickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    class AddButtonViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(item: SpeedDialItem){
            itemView.speed_dial_list_item_image.setImageResource(item.icon)
            itemView.title.text = itemView.context.resources.getString(R.string.new_tab)
        }
    }

    class SpeedDialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SpeedDialItem, clickListener: (String) -> Unit) {
            doAsync {
                var iStream: InputStream? = null
                var bitmap: Bitmap? = null
                var url: URL? = null
                var connection: URLConnection? = null
                try {
                    bitmap = iconCache[item.url]
                    if (bitmap == null) {
                        url = URL("https", item.url, "/favicon.ico")
                        Log.d("MYURL", "${url.ref}, $url")
                        connection = url.openConnection()
                        connection?.connect()
                        iStream = connection?.getInputStream()
                        bitmap = BitmapFactory.decodeStream(iStream)
                        iStream?.close()
                        iconCache[item.url] = bitmap
                    }
                    uiThread {
                        itemView.speed_dial_list_item_image.setImageBitmap(bitmap)
                    }

                    var title = titleCache[item.url]
                    if (title == null) {
                        url = URL("https://${item.url}")
                        connection = url.openConnection()
                        iStream = connection.getInputStream()
                        val reader = BufferedReader(InputStreamReader(iStream, "UTF-8"))
                        val sb = StringBuilder()
                        var line = reader.readLine()
                        while (line != null) {
                            sb.append(line)
                            line = reader.readLine()
                        }
                        val html = sb.toString()
                        if (html.contains("bing", true)) {
                            Log.d("MYURL", html)
                        }
                        title = html.substring(html.indexOf("<title>") + 7, html.indexOf("</title>"))
                        titleCache[item.url] = title
                        iStream.close()
                    }
                    uiThread {
                        itemView.title.text = title
                    }

                } catch (e: Exception) {
                    Log.e("MYURL", e.message)
                    uiThread {
                        itemView.speed_dial_list_item_image.setImageResource(item.icon)
                    }
                } finally {
                    iStream?.close()
                }
            }
            itemView.setOnClickListener {
                clickListener(item.url)
            }
        }
    }
}