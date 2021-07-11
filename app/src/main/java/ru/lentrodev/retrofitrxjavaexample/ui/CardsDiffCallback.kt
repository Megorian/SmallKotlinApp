package ru.lentrodev.retrofitrxjavaexample.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.lentrodev.retrofitrxjavaexample.db.model.Card

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
class CardsDiffCallback: DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }

}