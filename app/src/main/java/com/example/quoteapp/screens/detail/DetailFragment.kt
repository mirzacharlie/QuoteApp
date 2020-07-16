package com.example.quoteapp.screens.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import javax.inject.Inject
import com.example.quoteapp.R
import com.example.quoteapp.di.ViewModelInjection
import com.example.quoteapp.base.BaseFragment
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

    private var isFavourite = 0
    var id = 0L

    private val args: DetailFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initQuoteWithAuthor(args.id)

        ivFav.setOnClickListener {
            viewModel.changeFavourite(id, isFavourite)
            viewModel.initQuoteWithAuthor(id)
            if (isFavourite == 0) {
                Toast.makeText(activity, "Цитата добавлена в избранное", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Цитата удалена из избранного", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.quoteWithAuthor.observe(viewLifecycleOwner, Observer {
            tvQuote.text = it.quoteText
            tvAuthor.text = it.quoteAuthor
            isFavourite = it.isFavourite
            if(it.quoteId != null) id = it.quoteId
            ivPhoto.load(it.imgUri) {
                placeholder(R.drawable.author_placeholder)
                fallback(R.drawable.author_placeholder)
            }
            if (isFavourite == 0) {
                ivFav.setImageResource(R.drawable.ic_favorite_border_red_24dp)
            } else {
                ivFav.setImageResource(R.drawable.ic_favorite_red_24dp)
            }
        })
    }
}