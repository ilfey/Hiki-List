package com.ilfey.shikimori.ui.favorites

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.base.BaseViewHolder
import com.ilfey.shikimori.databinding.ItemFavoritesBinding
import com.ilfey.shikimori.di.network.models.Favorites

class ListAdapter(
    private val onClick: (Favorites.Entry) -> Unit = {},
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<Favorites.Entry> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Favorites.Entry>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    inner class ViewHolder(
        private val binding: ItemFavoritesBinding,
    ) : BaseViewHolder<Favorites.Entry>(binding.root) {

        override fun bind(item: Favorites.Entry) {

            with(binding) {
                Glide
                    .with(image.context)
                    .load(item.image)
                    .into(image)

                if (settings.isEnLocale) {
                    primaryTitle.text = item.titleEn
                    secondaryTitle.text = item.titleRu
                } else {
                    primaryTitle.text = item.titleRu
                    secondaryTitle.text = item.titleEn
                }

                if (!settings.fullTitles) {
                    primaryTitle.maxLines = 2
                    primaryTitle.ellipsize = TextUtils.TruncateAt.END
                    secondaryTitle.maxLines = 2
                    secondaryTitle.ellipsize = TextUtils.TruncateAt.END
                }

                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }
    }
}