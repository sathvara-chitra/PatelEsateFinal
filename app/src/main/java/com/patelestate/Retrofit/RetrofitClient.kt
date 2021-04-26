package com.crownfantancy.ui.Retrofit

import com.patelestate.utils.SharedPrefsConstant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    val client = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .build()
    var retrofit  = Retrofit.Builder()
    .client(client)
    .baseUrl(SharedPrefsConstant.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


    fun getClient(): APIService {
        return retrofit.create<APIService>(APIService::class.java)

    }



}