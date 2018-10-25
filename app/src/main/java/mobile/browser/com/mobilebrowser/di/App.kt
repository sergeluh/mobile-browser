package mobile.browser.com.mobilebrowser.di

import com.google.gson.Gson
import mobile.browser.com.mobilebrowser.api.ApiInterface
import mobile.browser.com.mobilebrowser.ui.main.MainViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val viewModelModule = applicationContext {
    viewModel {MainViewModel()}
}

private val networkModule = applicationContext {
    bean { Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(Gson())).baseUrl("https://google.com").build() }
    bean { get<Retrofit>().create(ApiInterface::class.java) }
}

val appModules = mutableListOf(viewModelModule, networkModule)