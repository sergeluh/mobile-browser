package mobile.browser.com.mobilebrowser.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import mobile.browser.com.mobilebrowser.R
import mobile.browser.com.mobilebrowser.utils.SingleToast

abstract class BaseActivity: AppCompatActivity(){

    protected fun replaceFragment(fragment: BaseFragment, tag: String){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag).commit()
    }

    protected fun addFragment(fragment: BaseFragment, tag: String){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack("my_stack")
                .commit()
    }

    protected fun popBackstack(){
        supportFragmentManager.popBackStack()
    }

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
        SingleToast.showToast(this, message, duration)
    }

    fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}