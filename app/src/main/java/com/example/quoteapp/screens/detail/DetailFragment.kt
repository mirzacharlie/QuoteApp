package com.example.quoteapp.screens.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import javax.inject.Inject
import com.example.quoteapp.R
import com.example.quoteapp.di.ViewModelInjection
import com.example.quoteapp.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: DetailVM

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setQuoteWithAuthor(args.id)

        viewModel.quoteWithAuthor.observe(viewLifecycleOwner, Observer {
            tvQuote.text = it.quoteText
            tvAuthor.text = it.quoteAuthor
            ivPhoto.load(it.imgUri){ placeholder(R.drawable.author_placeholder)}
        })
    }
}