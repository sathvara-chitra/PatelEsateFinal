package com.crownfantancy.ui.Retrofit


import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.patelestate.model.GetCommercialLIst
import com.patelestate.model.ResedentialListData
import com.patelestate.model.Residential.GetResidentialList
import com.patelestate.model.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url


interface APIService {

    @GET
    fun GetCommercialList(@Url fullUrl: String?): Call<JsonArray>


    // get login
    @GET
    fun getResedentialProperty(@Url fullUrl: String?): Call<JsonArray>

    @Headers("Content-Type: application/json")
    @GET
    fun getResedentialProperty1(@Url fullUrl: String?): Call<GetResidentialList>
    //Get Commercial  and resedential details
    @GET
    fun getDetails1(@Url fullUrl: String?): Call<JsonObject?>



}