package com.ilfey.shikimori.ui.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.databinding.ItemMessageBinding
import com.ilfey.shikimori.di.network.models.Message

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<Message> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Message>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    inner class ViewHolder(
        private val binding: ItemMessageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Message) {
            Glide
                .with(binding.image.context)
                .load(BuildConfig.APP_URL + item.linked.image.original)
                .into(binding.image)

            binding.body.text = item.html_body
            binding.title.text = item.linked.russian
            binding.name.text = item.linked.name
            binding.status.text = item.linked.status.name
        }
    }
}