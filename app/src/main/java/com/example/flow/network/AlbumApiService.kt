package com.example.flow.network

import com.example.flow.entities.Articles
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Only Accessible within the file.
private const val BASE_URL = "https://flo.health/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

//The implementation of the interface is done by Retrofit.
//Defines how Retrofit talks to the web server using HTTP requests.
interface FlowApiService {
    @GET("menstrual-cycle/health/emotions")
    fun emotionsResults(
        @Query("page") page: String,
        @Query("format") format: String,
        @Query("onpage") onPage: String
    ): Call<Articles>

    @GET("menstrual-cycle/health/vaginal-discharge")
    fun dischargeResults(
        @Query("page") page: String,
        @Query("format") format: String,
        @Query("onpage") onPage: String
    ): Call<Articles>

    @GET("menstrual-cycle/health/period")
    fun periodResults(
        @Query("page") page: String,
        @Query("format") format: String,
        @Query("onpage") onPage: String
    ): Call<Articles>

    @GET("menstrual-cycle/health/ovulation")
    fun ovulationResults(
        @Query("page") page: String,
        @Query("format") format: String,
        @Query("onpage") onPage: String
    ): Call<Articles>
}

//call     GithubApi.retrofitService()
object FlowApi {
    val retrofitService: FlowApiService by lazy { //Lazy means will not create until call to object.
        retrofit.create(FlowApiService::class.java)   //This is expensive. Thus the singleton.
        //This implements the Interface class. The body of each method.
    }
}













