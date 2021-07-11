package ru.lentrodev.retrofitrxjavaexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.lentrodev.retrofitrxjavaexample.db.model.User

/**
 * Created by Igor Gusakov on 18.06.2021.
 */
class MainFragment: Fragment() {

    var userNameField: TextView? = null
    var testStringField: TextView? = null
    var testString: String? = null
    var userName: String? = null
    var list: List<String>? = null
    var diffCallback = object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.mainfragment, container, false);
        userNameField = view.findViewById<TextView>(R.id.username)

        testStringField = view.findViewById<TextView>(R.id.teststring)

        var viewModelFactory = MainViewModelFactory(
            User(
                4,
                userName
            )
        )
        var bs :MainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        bs.testString = testString
        userNameField?.text = bs.user?.name
        testStringField?.text = bs.testString ?: ""

        userNameField?.setOnClickListener {
            bs.showLoading.set(!bs.showLoading.get()!!)
        }

        bs.livD?.observe(viewLifecycleOwner, Observer {

        })

        var c = MutableLiveData<User>()

        val sd = ArrayList<String>()

        view.findViewById<TextView>(R.id.btn).setOnClickListener {
          /*  val options = navOptions {
                anim {
                    popEnter = R.anim.sd
                }
            }*/
          //  findNavController().navigate(R.id.secondaryFragment, null)
        }
        return view
    }

    inline fun <reified T> prtr(a: Number) {
        if (a is T) {

        }
    }


    inner class AdP: PagingDataAdapter<String, ListItemHolder>(diffCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
            TODO("Not yet implemented")
        }
        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
            TODO("Not yet implemented")
        }
    }

    class ListAdapter: RecyclerView.Adapter<ListItemHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {

            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
            TODO("Not yet implemented")
        }
    }

    class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}