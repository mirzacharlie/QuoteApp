package com.example.quoteapp.screens.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.base.BaseFragment
import com.example.quoteapp.R
import com.example.quoteapp.adapters.QuoteAdapter
import com.example.quoteapp.di.ViewModelInjection
import kotlinx.android.synthetic.main.fragment_favourite.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavouriteFragment : BaseFragment(R.layout.fragment_favourite) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    @ViewModelInjection
    lateinit var viewModel: FavouriteVM

    lateinit var adapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = QuoteAdapter()
        rvQuotes.adapter = adapter
        adapter.onQuoteClickListener = object : QuoteAdapter.OnQuoteClickListener {
            override fun onQuoteClick(position: Int) {

                val action = adapter.quoteList[position].quoteId?.let {
                    FavouriteFragmentDirections
                        .actionFavouriteFragmentToDetailFragment(it)
                }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }
        }

        adapter.onQuoteLongClickListener = object : QuoteAdapter.OnQuoteLongClickListener {
            override fun onQuoteLongClick(position: Int): Boolean {
                viewModel.removeFromFavourite(adapter.quoteList[position])
                return true
            }
        }

        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    viewModel.deleteQuote(adapter.quoteList[viewHolder.adapterPosition])
                }
            })
        itemTouchHelper.attachToRecyclerView(rvQuotes)

        viewModel.favouriteQuoteList.observe(viewLifecycleOwner, Observer {
            adapter.quoteList = it
        })
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
