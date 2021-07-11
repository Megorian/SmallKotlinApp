package ru.lentrodev.retrofitrxjavaexample.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.lentrodev.retrofitrxjavaexample.CustomApplication
import ru.lentrodev.retrofitrxjavaexample.api.ApiAccess
import ru.lentrodev.retrofitrxjavaexample.db.CardsRepository
import ru.lentrodev.retrofitrxjavaexample.api.RequestErrorData
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import ru.lentrodev.retrofitrxjavaexample.di.DaggerAppComponent
import javax.inject.Inject

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
class CardsViewModel: ViewModel() {
    val cardsList: MutableLiveData<List<Card>> = MutableLiveData<List<Card>>()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorData: MutableLiveData<RequestErrorData> = MutableLiveData()
    @Inject
    lateinit var preferences: SharedPreferences
    @Inject
    lateinit var repository: CardsRepository

    var cardsRequestPage: Int = 1

    var chosenItem: Card? = null

    fun initDbData() {
        cardsRequestPage = preferences.getInt("cards_last_request_page", 1)
        if (repository.isRepoInitialized()) {
            updateFromSourcesAtInit()
        } else {
            isLoading.postValue(true)

            Observable.fromCallable {
                while (!repository.isRepoInitialized()) {
                    //waiting for initialization of repository on another thread
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateFromSourcesAtInit()
                }
        }
    }

    private fun updateFromSourcesAtInit() {
        when (repository.allCards.size) {
            0 -> updateCards(true)
            else -> {
                cardsList.postValue(repository.allCards)
                isLoading.postValue(false)
            }
        }
    }

    fun onLastItemInListVisible() {
        updateCards()
    }

    fun updateCards() {
        updateCards(false)
    }

    fun updateCards(forceUpdate: Boolean) {
        val currentlyLoading = isLoading.value ?: false
        if (!currentlyLoading || forceUpdate) {
            isLoading.postValue(true)
            var c = ApiAccess.api.getCards(cardsRequestPage)
                .subscribeOn(Schedulers.io())
                .map {
                    repository.allCards =
                        if (repository.allCards == null) it
                        else repository.allCards?.plus(it)?.distinct()
                    repository.insertCards(it)
                    cardsRequestPage++
                    preferences.edit().putInt("cards_last_request_page", cardsRequestPage).apply()
                    it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<Card>> {
                    override fun onError(e: Throwable?) {
                        if (e is HttpException) {
                            errorData.postValue(RequestErrorData(e.code(), e.message()))
                        } else {
                            errorData.postValue(RequestErrorData(0, ""))
                        }
                        e?.printStackTrace()
                        isLoading.postValue(false)
                    }

                    override fun onSuccess(t: List<Card>?) {
                        val newValue =
                            if (cardsList.value == null) t else cardsList.value?.plus(t!!)
                                ?.distinct()
                        cardsList.postValue(newValue)
                        isLoading.postValue(false)
                    }
                    override fun onSubscribe(d: Disposable?) {
                        //do nothing
                    }

                })
        }
    }

    fun onItemChosen(chosenItem: Card) {
        this.chosenItem = chosenItem
    }
}