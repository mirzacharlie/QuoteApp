package com.example.quoteapp.screens.settings

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.quoteapp.base.BaseFragment
import com.example.quoteapp.R
import com.example.quoteapp.SyncManager
import com.example.quoteapp.di.ViewModelInjection
import com.example.quoteapp.screens.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: SettingsVM
    @Inject
    lateinit var syncManager: SyncManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
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

}
