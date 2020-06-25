package com.example.quoteapp.screens.quotelist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.quoteapp.BaseFragment
import com.example.quoteapp.R
import com.example.quoteapp.SyncManager
import com.example.quoteapp.adapters.QuoteAdapter
import com.example.quoteapp.di.ViewModelInjection
import com.example.quoteapp.screens.detail.DetailFragment
import com.example.quoteapp.workers.DownloadWorker
import com.example.quoteapp.workers.NotificationWorker
import kotlinx.android.synthetic.main.fragment_quote_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QuoteListFragment : BaseFragment(R.layout.fragment_quote_list) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    @ViewModelInjection
    lateinit var viewModel: QuoteListVM

    @Inject
    lateinit var syncManager: SyncManager

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
        return inflater.inflate(R.layout.fragment_quote_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabLoad.setOnClickListener { syncManager.oneTimeSync() }

        adapter = QuoteAdapter()
        rvQuotes.adapter = adapter
        adapter.onQuoteClickListener = object : QuoteAdapter.OnQuoteClickListener {
            override fun onQuoteClick(position: Int) {

                val action = adapter.quoteList[position].quoteId?.let {
                    QuoteListFragmentDirections
                        .actionQuoteListFragmentToDetailFragment(it)
                }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }
        }

        adapter.onQuoteLongClickListener = object : QuoteAdapter.OnQuoteLongClickListener {
            override fun onQuoteLongClick(position: Int): Boolean {
                viewModel.addToFavourite(adapter.quoteList[position])
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

        viewModel.quoteList.observe(viewLifecycleOwner, Observer {
            adapter.quoteList = it
        })

        viewModel.status.observe(viewLifecycleOwner, Observer<List<WorkInfo>> { workInfo ->
            val currentWorkStatus = workInfo?.getOrNull(0)

            var isWorkActive = false
            if (currentWorkStatus?.state == WorkInfo.State.RUNNING) {
                isWorkActive = true
            } else if (currentWorkStatus?.state == WorkInfo.State.ENQUEUED) {
                isWorkActive = false
            }

            progressBar.visibility = if (isWorkActive) View.VISIBLE else View.GONE
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuoteListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
