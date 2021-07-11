package ru.lentrodev.retrofitrxjavaexample.api

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.lentrodev.retrofitrxjavaexample.api.deserializers.CardsListDeserializer
import ru.lentrodev.retrofitrxjavaexample.db.model.Card

/**
 * Created by Igor Gusakov on 16.06.2021.
 */
object ApiAccess {

    var api: ApiService

    init {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.magicthegathering.io/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson().newBuilder().registerTypeAdapter(List::class.java, CardsListDeserializer()).create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        api = retrofit.create(ApiService::class.java)
    }
}