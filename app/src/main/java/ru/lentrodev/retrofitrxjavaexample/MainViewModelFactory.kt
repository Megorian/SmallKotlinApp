package ru.lentrodev.retrofitrxjavaexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Igor Gusakov on 18.06.2021.
 */
class MainViewModelFactory: ViewModelProvider.NewInstanceFactory {
    var s: String? = null
    constructor(s: String) : super() {
        this.s = s
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(s) as T
        }
        return super.create(modelClass)
    }

}