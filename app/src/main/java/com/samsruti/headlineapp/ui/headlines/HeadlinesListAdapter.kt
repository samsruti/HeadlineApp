package com.samsruti.headlineapp.ui.headlines


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samsruti.headlineapp.databinding.ListItemHeadlinesBinding

import com.samsruti.headlineapp.model.Article

class HeadlinesListAdapter (private val clickListener: CallBackClickListener):
    PagedListAdapter<Article, HeadlinesListAdapter.HeadlinesViewHolder>(DiffCallback) {

    class HeadlinesViewHolder(private var dataBinding: ListItemHeadlinesBinding)
        : RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(networkNews: Article){
            dataBinding.headline = networkNews
            dataBinding.executePendingBindings()
        }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        return HeadlinesViewHolder(ListItemHeadlinesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        val currentNews = getItem(position)
        currentNews?.let {
            holder.itemView.setOnClickListener {
                clickListener.onClick(currentNews)
//            Toast.makeText(holder.itemView.context,currentNews.toString(),Toast.LENGTH_LONG).show()
            }
            holder.bind(currentNews)
        }


    }

    class CallBackClickListener(val clickListener: (networkNews: Article) -> Unit) {
        fun onClick(networkNews: Article) = clickListener(networkNews)
    }



}