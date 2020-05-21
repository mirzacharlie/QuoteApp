package com.example.quoteapp.screens.settings

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.work.*
import com.example.quoteapp.R
import com.example.quoteapp.di.ViewModelInjection
import com.example.quoteapp.screens.quotelist.QuoteListVM
import com.example.quoteapp.utils.*
import com.example.quoteapp.workers.DownloadWorker
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var pref: SharedPreferences
    private var currentRepeatInterval = REPEAT_INTERVAL_0

    @Inject
    @ViewModelInjection
    lateinit var viewModel: SettingsVM

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

        pref = requireActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        // Получаю интервал синхронизации из Shared Preference и определяю позицию для спинера
        if(pref.contains(APP_PREFERENCES_REPEAT_INTERVAL)){
            var position = 0
            when(pref.getInt(APP_PREFERENCES_REPEAT_INTERVAL, 0)){
                REPEAT_INTERVAL_0 ->{
                    position = 0
                    currentRepeatInterval = REPEAT_INTERVAL_0
                }
                REPEAT_INTERVAL_1 ->{
                    position = 1
                    currentRepeatInterval = REPEAT_INTERVAL_1
                }
                REPEAT_INTERVAL_2 ->{
                    position = 2
                    currentRepeatInterval = REPEAT_INTERVAL_2
                }
                REPEAT_INTERVAL_3 ->{
                    position = 3
                    currentRepeatInterval = REPEAT_INTERVAL_3
                }
                REPEAT_INTERVAL_4 ->{
                    position = 4
                    currentRepeatInterval = REPEAT_INTERVAL_4
                }
            }
            spinner.setSelection(position)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                viewModel.setInterval(position)

//                var selectedInterval = 0
//
//                when(position){
//                    0 -> selectedInterval = REPEAT_INTERVAL_0
//                    1 -> selectedInterval = REPEAT_INTERVAL_1
//                    2 -> selectedInterval = REPEAT_INTERVAL_2
//                    3 -> selectedInterval = REPEAT_INTERVAL_3
//                    4 -> selectedInterval = REPEAT_INTERVAL_4
//                }
//
//                    //Обновляю время синхронизации, если выбранное отличается от текущего
//                if (selectedInterval != currentRepeatInterval){
//                    val editor = pref.edit()
//                    editor.putInt(APP_PREFERENCES_REPEAT_INTERVAL, selectedInterval)
//                    editor.apply()
//
//                    val constraints: Constraints = Constraints.Builder()
//                        .setRequiredNetworkType(NetworkType.CONNECTED)
//                        .build()
//
//                    val request = PeriodicWorkRequestBuilder<DownloadWorker>(selectedInterval.toLong(), TimeUnit.HOURS)
//                        .setConstraints(constraints)
//                        .build()
//
//                    WorkManager.getInstance(requireActivity()).enqueueUniquePeriodicWork(DownloadWorker.TAG,
//                        ExistingPeriodicWorkPolicy.KEEP, request)
//                    Log.d("WORK_MANAGER", "New repeat interval is: $selectedInterval hours")
//                }

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
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
