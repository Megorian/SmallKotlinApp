package ru.lentrodev.retrofitrxjavaexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cards_list.*
import ru.lentrodev.retrofitrxjavaexample.CustomApplication
import ru.lentrodev.retrofitrxjavaexample.R
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import ru.lentrodev.retrofitrxjavaexample.di.AppModule
import ru.lentrodev.retrofitrxjavaexample.di.DaggerAppComponent

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
class CardsFragment: Fragment() {

    lateinit var cardsList: RecyclerView
    lateinit var cardsAdapter: CardsAdapter
    lateinit var loadingDialog: View
    var viewModel: CardsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cards_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = loading_screen
        cardsList = list
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider_line)!!)
        }
        cardsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cardsList.addItemDecoration(itemDecoration)
        cardsAdapter = CardsAdapter()
        cardsList.adapter = cardsAdapter
        cardsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemsCount = layoutManager.childCount
                val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()
                if (visibleItemsCount + lastVisibleItemPos + 3 >= totalItemCount) {
                    viewModel?.onLastItemInListVisible()
                }
            }
        })

        viewModel = ViewModelProvider(requireActivity()).get(CardsViewModel::class.java).apply {
            DaggerAppComponent.builder()
                .appModule(AppModule(application = context?.applicationContext as CustomApplication))
                .build()
                .inject(this)
            initDbData()
        }

        viewModel?.cardsList?.observe(viewLifecycleOwner, Observer {
            cardsAdapter.submitList(it)
        })
        viewModel?.isLoading?.observe(viewLifecycleOwner, Observer {
            loadingDialog.visibility = if (it) View.VISIBLE else View.GONE
        })
    }


    inner class CardsAdapter : ListAdapter<Card, CardViewHolder>(CardsDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val holderView =
                LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
            return CardViewHolder(holderView)
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
            val item: Card = getItem(position)
            if (!item.cardImageUrl.isNullOrEmpty()) {
                Picasso.get().load(item.cardImageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.cardImage, object : Callback {
                        override fun onSuccess() {}
                        override fun onError(e: Exception) {
                            Picasso.get().load(item.cardImageUrl)
                                .into(holder.cardImage)
                        }
                    })
            } else {
                holder.cardImage.setImageDrawable(null)
            }
            holder.cardName.text = item.cardName
            holder.cardAuthor.text = item.cardAuthor
            holder.itemView.setOnClickListener {
                viewModel?.onItemChosen(item)
                findNavController().navigate(R.id.cardDetailsFragment, null)
            }
        }
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardImage = itemView.findViewById<ImageView>(R.id.image)
        var cardName = itemView.findViewById<TextView>(R.id.name)
        var cardAuthor = itemView.findViewById<TextView>(R.id.author)
    }
}