package com.example.quoteapp.screens.favourite

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.base.BaseFragment
import com.example.quoteapp.R
import com.example.quoteapp.adapters.QuoteAdapter
import com.example.quoteapp.di.ViewModelInjection
import com.example.quoteapp.screens.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_favourite.*
import javax.inject.Inject

class FavouriteFragment : BaseFragment(R.layout.fragment_favourite) {

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: FavouriteVM
    lateinit var adapter: QuoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

}
