package mobile.browser.com.mobilebrowser.ui.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

import mobile.browser.com.mobilebrowser.R
import mobile.browser.com.mobilebrowser.base.BaseFragment
import mobile.browser.com.mobilebrowser.ui.main.adapter.SpeedDialAdapter
import mobile.browser.com.mobilebrowser.ui.main.model.SpeedDialItem
import org.koin.android.architecture.ext.sharedViewModel

class MainFragment : BaseFragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val speedDialItems = mutableListOf(SpeedDialItem(R.drawable.ic_add, "not_url"),
                SpeedDialItem(R.drawable.ic_home, "google.com"),
                SpeedDialItem(R.drawable.ic_home, "amazon.com"),
                SpeedDialItem(R.drawable.ic_home, "youtube.com"),
                SpeedDialItem(R.drawable.ic_home, "apple.com"),
                SpeedDialItem(R.drawable.ic_home, "facebook.com"),
                SpeedDialItem(R.drawable.ic_menu, "developer.android.com"),
                SpeedDialItem(R.drawable.ic_rectangle, "twitter.com"),
                SpeedDialItem(R.drawable.ic_refresh, "wikipedia.org"))

        viewModel.onQueryChanged("", true)

        speed_dial_list.layoutManager = GridLayoutManager(context, 5)
        speed_dial_list.adapter = SpeedDialAdapter(speedDialItems){
            viewModel.url.value = "https://$it"
            viewModel.onGoToWebViewFragment()
        }
    }

    companion object {
        const val TAG = "main_fragment"

        fun newInstance() = MainFragment()
    }
}
