package mobile.browser.com.mobilebrowser.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface{

    @GET
    fun getTitle(@Url url: String): Call<String>

}