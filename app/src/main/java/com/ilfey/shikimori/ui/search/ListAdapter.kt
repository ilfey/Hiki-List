package com.ilfey.shikimori.ui.search

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.base.BaseViewHolder
import com.ilfey.shikimori.databinding.ItemSearchBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.ui.anime.AnimeActivity
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.visible
import java.util.*

class ListAdapter(
    private var list: List<AnimeItem>?,
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<AnimeItem>?) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list!![pos])
    }

    inner class ViewHolder(
        private val binding: ItemSearchBinding,
    ) : BaseViewHolder<AnimeItem>(binding.root) {

        override fun bind(item: AnimeItem) {
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

                status.text = item.status

                if (item.score != 0f) {
                    score.rating = item.score
                    score.visible()
                } else {
                    score.gone()
                }

                if (item.episodes != null) {
                    episodes.text = item.episodes
                    episodes.visible()
                } else {
                    episodes.gone()
                }

                root.setOnClickListener {
                    val intent = AnimeActivity.newIntent(root.context, item.id)
                    root.context.startActivity(intent)
                }
            }
        }
    }
}