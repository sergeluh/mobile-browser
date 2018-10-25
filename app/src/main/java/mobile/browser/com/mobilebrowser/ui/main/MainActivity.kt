package mobile.browser.com.mobilebrowser.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mobile.browser.com.mobilebrowser.R
import mobile.browser.com.mobilebrowser.base.BaseActivity
import mobile.browser.com.mobilebrowser.ui.main.adapter.SearchEngineListAdapter
import mobile.browser.com.mobilebrowser.ui.main.model.SearchEngine
import org.jetbrains.anko.sdk27.coroutines.onFocusChange
import org.koin.android.architecture.ext.viewModel



class MainActivity : BaseActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar_container.requestFocus()

        val engineList = mutableListOf(SearchEngine(R.drawable.icon_google, "https://google.com/search?q="),
                SearchEngine(R.drawable.icon_bing, "http://bing.com/search?q="),
                SearchEngine(R.drawable.icon_yahoo, "https://search.yahoo.com/search?p="),
                SearchEngine(R.drawable.icon_duckduckgo, "https://duckduckgo.com/?q="))
        val adapter = SearchEngineListAdapter(this, engineList)
        adapter.setDropDownViewResource(R.layout.search_list_item)
        search_engine_list.adapter = adapter

        viewModel.goToMainFragment.observe(this, Observer {
            replaceFragment(MainFragment.newInstance(), MainFragment.TAG)
        })

        viewModel.goToWebViewFragment.observe(this, Observer {
            addFragment(WebViewFragment.newInstance(), WebViewFragment.TAG)
        })

        viewModel.query.observe(this, Observer {
            search_view.setText(viewModel.query.value)
        })

        viewModel.onGoToMainFragment()

        search_view.onFocusChange { _, hasFocus ->
            if (!hasFocus){
                hideKeyboard()
            }
        }

        search_view.setOnEditorActionListener { _, _, _ ->
            val query = search_view.text.toString().trim()
            if (query.isNotEmpty() && query.contains(".") && !query.contains(" ")){
                viewModel.url.value = query
            }else{
//                viewModel.url.value = engineList[search_engine_list.selectedItemPosition].url + query
                viewModel.url.value = "https://google.com/search?q=$query"
            }
            viewModel.onGoToWebViewFragment()
            false
        }


    }
}
