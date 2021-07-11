package ru.lentrodev.retrofitrxjavaexample.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.lentrodev.retrofitrxjavaexample.api.ApiAccess
import ru.lentrodev.retrofitrxjavaexample.api.ApiService
import ru.lentrodev.retrofitrxjavaexample.api.deserializers.CardsListDeserializer
import javax.inject.Singleton

/**
 * Created by Igor Gusakov on 11.07.2021.
 */
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
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
        return retrofit.create(ApiService::class.java)
    }
}