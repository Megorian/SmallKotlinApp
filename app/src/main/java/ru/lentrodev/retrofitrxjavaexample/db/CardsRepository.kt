package ru.lentrodev.retrofitrxjavaexample.db

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.lentrodev.retrofitrxjavaexample.CustomApplication
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
@Singleton class CardsRepository @Inject constructor(val db: RoomDB) {

    lateinit var allCards: List<Card>

    init {
        Observable.fromCallable{
            allCards = db.cardDao()?.loadAll() ?: emptyList()
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun isRepoInitialized() = this::allCards.isInitialized

    fun insertCards(cards: List<Card>) {
        Completable.fromAction {
            db.cardDao()?.insertBatch(cards)
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}