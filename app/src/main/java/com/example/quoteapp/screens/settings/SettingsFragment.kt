package com.example.quoteapp.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.quoteapp.base.BaseFragment
import com.example.quoteapp.R
import com.example.quoteapp.SyncManager
import com.example.quoteapp.di.ViewModelInjection
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    @ViewModelInjection
    lateinit var viewModel: SettingsVM

    @Inject
    lateinit var syncManager: SyncManager

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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.download_time, R.layout.custom_spinner_item
            )
        }
        adapter?.setDropDownViewResource(R.layout.custom_drop_down_spinner_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setIntervalByPosition(position)
            }
        }

        switchQuoteOfTheDay.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setQuoteOfTheDayActive(isChecked)
        }

        viewModel.currentSpinnerPositionByInterval.observe(viewLifecycleOwner, Observer {
            spinner.setSelection(it)
        })

        viewModel.currentQuoteOfTheDaySwitchPosition.observe(viewLifecycleOwner, Observer {
            switchQuoteOfTheDay.isChecked = it
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
