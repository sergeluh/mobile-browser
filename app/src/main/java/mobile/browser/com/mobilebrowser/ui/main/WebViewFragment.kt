package mobile.browser.com.mobilebrowser.ui.main


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_web_view.*

import mobile.browser.com.mobilebrowser.R
import mobile.browser.com.mobilebrowser.base.BaseFragment
import org.koin.android.architecture.ext.sharedViewModel

class WebViewFragment : BaseFragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        web_view.isAdblockEnabled = true

        web_view.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("MYURL", "$url")
                viewModel.onQueryChanged(url, true)
            }
        }
        viewModel.url.observe(viewLifecycleOwner, Observer {
            if (viewModel.validateUrl(it)){
                web_view.loadUrl(it)
            }
        })
    }

    companion object {
        const val TAG = "webViewFragment"

        fun newInstance() = WebViewFragment()
    }

}
