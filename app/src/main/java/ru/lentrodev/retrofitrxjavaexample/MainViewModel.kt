package ru.lentrodev.retrofitrxjavaexample

import android.os.Handler
import android.os.Looper
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.lentrodev.retrofitrxjavaexample.db.model.Card

/**
 * Created by Igor Gusakov on 16.06.2021.
 */
class MainViewModel() : ViewModel() {
    var testString: String? = null
    var list: List<String>? = null
    var livD: LiveData<Boolean>? = MutableLiveData<Boolean>()

    var showLoading = ObservableField<Boolean>()

    fun refresh() {
        val h = Handler(Looper.getMainLooper())

        val testFlow: Flow<PagingData<Card>>? = null

        testFlow?.map { pagingData ->
            pagingData.insertSeparators { card: Card?, card2: Card? ->
                if (card == null) {
                    return@insertSeparators null
                }
                null
            }
        }

        viewModelScope.launch {
            println("TEST1 IN MAINVIEWMODEL COROUTINE SCOPE " + this)
        }
    }

    fun getFlow() : Flow<PagingData<Card>>? {
        return null
    }

}