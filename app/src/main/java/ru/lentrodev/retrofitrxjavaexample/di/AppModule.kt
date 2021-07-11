package ru.lentrodev.retrofitrxjavaexample.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.lentrodev.retrofitrxjavaexample.CustomApplication
import ru.lentrodev.retrofitrxjavaexample.db.RoomDB
import javax.inject.Singleton

/**
 * Created by Igor Gusakov on 11.07.2021.
 */
@Module
class AppModule (application: CustomApplication) {

    private var application: CustomApplication = application

    @Provides
    @Singleton
    fun provideAppDatabase(
        context: Context
    ): RoomDB {
        return Room.databaseBuilder(context, RoomDB::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE)
    }

}