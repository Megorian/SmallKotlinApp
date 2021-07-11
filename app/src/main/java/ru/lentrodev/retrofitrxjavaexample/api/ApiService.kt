package ru.lentrodev.retrofitrxjavaexample.api

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import ru.lentrodev.retrofitrxjavaexample.db.model.CardSet

/**
 * Created by Igor Gusakov on 16.06.2021.
 */
interface ApiService {
    @GET("cards")
    fun getCards(@Query("page") page: Int) : Single<List<Card>>

    @GET("cards")
    fun getSets(@Path("id") id: Int, @Query("sort") sort: String) : Observable<List<CardSet>>
}