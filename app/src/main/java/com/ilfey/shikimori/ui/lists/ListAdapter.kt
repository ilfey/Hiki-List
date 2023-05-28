package com.ilfey.shikimori.ui.lists

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.R
import com.ilfey.shikimori.databinding.ItemAnimeListBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.ui.anime.AnimeActivity
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.visible
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(
    var _list: List<AnimeRate>?,
    private val showFullTitles: Boolean,
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<AnimeRate>?) {
        _list = l
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = _list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(_list!![pos])
    }

    inner class ViewHolder(
        private val binding: ItemAnimeListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        private val dateFormat = SimpleDateFormat(
            "dd MMMM yyyy",
            context.resources.configuration.locales.get(0),
        )

        fun bind(item: AnimeRate) {
            with(binding) {
                Glide
                    .with(image.context)
                    .load(item.anime.image)
                    .into(image)

                title.text = item.anime.titleRu
                if (!showFullTitles) {
                    title.maxLines = 2
                    title.ellipsize = TextUtils.TruncateAt.END
                }

                name.text = item.anime.titleEn

                if (item.score != null) {
                    userScore.text = item.score
                    userScore.visible()
                } else {
                    userScore.gone()
                }

                status.text = item.anime.status

                if (item.anime.score != 0f) {
                    score.rating = item.anime.score
                    score.visible()
                } else {
                    score.gone()
                }

                if (item.anime.episodes != null) {
                    episodes.text = item.anime.episodes
                    episodes.visible()
                } else {
                    episodes.gone()
                }

                root.setOnClickListener {
                    val intent = AnimeActivity.newIntent(root.context, item.anime.id)
                    root.context.startActivity(intent)
                }
            }
        }
    }
}