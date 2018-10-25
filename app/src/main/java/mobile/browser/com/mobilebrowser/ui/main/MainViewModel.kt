package mobile.browser.com.mobilebrowser.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.browser.com.mobilebrowser.utils.SingleLiveEvent

class MainViewModel: ViewModel(){
    val goToMainFragment = SingleLiveEvent<Unit>()
    val goToWebViewFragment = SingleLiveEvent<Unit>()

    val query = MutableLiveData<String>()
    val url = MutableLiveData<String>()

    fun onGoToMainFragment(){
        goToMainFragment.call()
    }

    fun onGoToWebViewFragment(){
        goToWebViewFragment.call()
    }

    fun onQueryChanged(newQuery: String?, notifyToolBar: Boolean){
        if (notifyToolBar) {
            query.value = newQuery
        }
    }

    fun validateUrl(url: String?): Boolean{
        if (url != null && url.isNotEmpty() && (url.startsWith("http://") || url.startsWith("https://"))){
            return true
        }
        return false
    }
}