package mobile.browser.com.mobilebrowser.utils

import android.content.Context
import android.widget.Toast

class SingleToast{
    companion object {
        private var toast: Toast? = null

        fun showToast(context: Context, message: String, duration: Int){
            toast?.cancel()
            toast = Toast.makeText(context, message, duration)
            toast?.show()
        }
    }
}