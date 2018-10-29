package mobile.browser.com.mobilebrowser.ui.main.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import mobile.browser.com.mobilebrowser.R
import mobile.browser.com.mobilebrowser.ui.main.model.SearchEngine

class SearchEngineListAdapter(context: Context, @LayoutRes private val engineList: MutableList<SearchEngine>): ArrayAdapter<SearchEngine>(context, 0, R.id.blank_text, engineList){
    override fun getCount() = engineList.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        if (view == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.search_list_item, parent, false)
        }
        val image = view?.findViewById<ImageView>(R.id.search_icon)
        view?.findViewById<ImageView>(R.id.dropdown_arrow)?.visibility = View.VISIBLE
        image?.setImageResource(engineList[position].icon)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        if (view == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.search_list_item, parent, false)
        }
        val image = view?.findViewById<ImageView>(R.id.search_icon)
        image?.setImageResource(engineList[position].icon)
        return view
    }
}