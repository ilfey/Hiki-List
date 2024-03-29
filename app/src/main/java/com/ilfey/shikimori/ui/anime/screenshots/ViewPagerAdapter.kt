package com.ilfey.shikimori.ui.anime.screenshots

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig

class ViewPagerAdapter(
    private val list: List<String>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ctx = parent.context
        val img = ImageView(ctx)
        img.scaleType = ImageView.ScaleType.CENTER_INSIDE
        img.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )

        return ViewHolder(img)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(
        private val img: ImageView,
    ) : RecyclerView.ViewHolder(img) {
        fun bind(item: String) {
            Glide
                .with(img.context)
                .load(item)
                .into(img)
        }
    }
}