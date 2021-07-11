package ru.lentrodev.retrofitrxjavaexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedDispatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_details.*
import ru.lentrodev.retrofitrxjavaexample.R

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
class CardDetailsFragment: Fragment() {

    var viewModel: CardsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CardsViewModel::class.java)
        val cardItem = viewModel?.chosenItem
        if (cardItem == null) {
            requireActivity().onBackPressed()
        } else {
            if (!cardItem.cardImageUrl.isNullOrEmpty()) {
                Picasso.get().load(cardItem.cardImageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(card_image, object : Callback {
                        override fun onSuccess() {}
                        override fun onError(e: Exception) {
                            Picasso.get().load(cardItem.cardImageUrl)
                                .into(card_image)
                        }
                    })
            } else {
                card_image.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.no_image))
            }
            card_name.text = viewModel?.chosenItem?.cardName ?: ""
            card_author.text = viewModel?.chosenItem?.cardAuthor ?: ""
        }
    }
}