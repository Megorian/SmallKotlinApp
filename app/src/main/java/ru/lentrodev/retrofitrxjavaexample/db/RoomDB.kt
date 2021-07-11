package ru.lentrodev.retrofitrxjavaexample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.lentrodev.retrofitrxjavaexample.db.dao.CardDao
import ru.lentrodev.retrofitrxjavaexample.db.dao.CardSetDao
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import ru.lentrodev.retrofitrxjavaexample.db.model.CardSet
import ru.lentrodev.retrofitrxjavaexample.db.model.User

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
@Database(
    entities = [Card::class, CardSet::class, User::class],
    version = 6
)
 abstract class RoomDB: RoomDatabase() {

    abstract fun cardDao(): CardDao?
    abstract fun cardSetDao(): CardSetDao?

}