package ru.lentrodev.retrofitrxjavaexample.di

import dagger.Component
import ru.lentrodev.retrofitrxjavaexample.CustomApplication
import ru.lentrodev.retrofitrxjavaexample.ui.CardsViewModel
import javax.inject.Singleton

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(viewModel: CardsViewModel)
}