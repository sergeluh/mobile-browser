package mobile.browser.com.mobilebrowser.ui.main


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
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

        web_view.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d("MYPROGRESS", "Progress is: $newProgress")
            }
        }

        web_view.settings.javaScriptEnabled = true

//        web_view.settings.userAgentString = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
//        web_view.settings.builtInZoomControls = true
    }

    override fun onBackPressed(): Boolean {
        web_view?.also {
            if (it.canGoBack()){
                it.goBack()
                return false
            }
        }
        return true
    }

    companion object {
        const val TAG = "webViewFragment"

        fun newInstance() = WebViewFragment()
    }

}
