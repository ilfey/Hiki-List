package com.ilfey.shikimori.ui.history

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.databinding.ItemHistoryBinding
import com.ilfey.shikimori.di.network.enums.HistoryItem

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<HistoryItem> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<HistoryItem>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    inner class ViewHolder(
        private val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryItem) {
            binding.description.text = Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)

            if (item.target == null) {
                binding.title.visibility = View.GONE
                binding.image.visibility = View.GONE
            } else {
                binding.title.text = item.target.russian

                Glide
                    .with(binding.image.context)
                    .load(BuildConfig.APP_URL + item.target.image.original)
                    .into(binding.image)
            }
        }
    }
}