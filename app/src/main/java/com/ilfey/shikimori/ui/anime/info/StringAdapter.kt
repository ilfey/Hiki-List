package com.ilfey.shikimori.ui.anime.info

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StringAdapter(
    private var list: List<String>,
) : RecyclerView.Adapter<StringAdapter.ViewHolder>() {

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<String>) {
        list = l

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ctx = parent.context
        val tv = TextView(ctx)
        tv.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        return ViewHolder(tv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(
        private val tv: TextView,
    ) : RecyclerView.ViewHolder(tv) {
        fun bind(item: String) {
            tv.text = item
        }
    }
}