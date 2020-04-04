package com.example.data.service

import com.example.data.model.Beer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

private const val BASE_URL = "https://api.punkapi.com/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val loggingInterceptor = HttpLoggingInterceptor()
private val client = OkHttpClient.Builder().addInterceptor(getInterceptor()).build()

private fun getInterceptor(): HttpLoggingInterceptor {
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface BeerApiService {

    @GET("beers")
    fun getAllBeers(
        @QueryMap filters: Map<String, String?>
    ): Deferred<List<Beer>>

    @GET("beers/{id}")
    fun getBeerById(@Path("id") id: Long) : Deferred<List<Beer>>

    @GET("beers/random")
    fun getRandomBeer() : Deferred<List<Beer>>
}

object BeerApi {
    val retrofitService : BeerApiService by lazy {
        retrofit.create(BeerApiService::class.java)
    }
}