package mobile.browser.com.mobilebrowser

import android.app.Application
import mobile.browser.com.mobilebrowser.di.appModules
import org.koin.android.ext.android.startKoin

class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(appModules)
    }
}