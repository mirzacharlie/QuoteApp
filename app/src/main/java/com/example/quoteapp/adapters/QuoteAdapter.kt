package com.example.quoteapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.R
import com.example.quoteapp.pojo.Quote
import kotlinx.android.synthetic.main.item_quote.view.*

class QuoteAdapter : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    lateinit var onQuoteClickListener: OnQuoteClickListener
    lateinit var onQuoteLongClickListener: OnQuoteLongClickListener

    var quoteList: List<Quote> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    interface OnQuoteClickListener {
        fun onQuoteClick(position: Int)
    }

    interface OnQuoteLongClickListener {
        fun onQuoteLongClick(position: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onQuoteClickListener.onQuoteClick(position) }
        holder.itemView.setOnLongClickListener { onQuoteLongClickListener.onQuoteLongClick(position) }
        val quote = quoteList[position]

        holder.tvQuote.text = quote.quoteText
        holder.tvAuthor.text = quote.quoteAuthor
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvQuote = itemView.tvQuote
        val tvAuthor = itemView.tvAuthor
    }

    override fun getItemCount() = quoteList.size
}