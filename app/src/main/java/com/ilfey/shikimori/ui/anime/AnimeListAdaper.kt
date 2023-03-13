package com.ilfey.shikimori.ui.anime

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class AnimeListAdaper(

) : RecyclerView.Adapter<AnimeListAdaper.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}