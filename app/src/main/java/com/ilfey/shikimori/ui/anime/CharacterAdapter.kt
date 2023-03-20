package com.ilfey.shikimori.ui.anime

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.databinding.ItemCharacterBinding
import com.ilfey.shikimori.di.network.models.Role

class CharacterAdapter(
    private var list: List<Role>,
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Role>) {
        list = l

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class ViewHolder(
        private val binding: ItemCharacterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Role, position: Int) {
            Glide
                .with(binding.card.context)
                .load(BuildConfig.APP_URL + item.character?.image?.original)
                .into(binding.card)

            binding.name.text = item.character?.russian
        }
    }
}