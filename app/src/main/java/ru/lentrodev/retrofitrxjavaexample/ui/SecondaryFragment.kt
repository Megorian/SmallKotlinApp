package ru.lentrodev.retrofitrxjavaexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.lentrodev.retrofitrxjavaexample.R

/**
 * Created by Igor Gusakov on 29.06.2021.
 */
class SecondaryFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.secondfragment, container, false)
        return v
    }
}