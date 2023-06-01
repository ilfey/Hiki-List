package com.ilfey.shikimori.ui.lists

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseViewHolder
import com.ilfey.shikimori.databinding.ItemAnimeListBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.ListType.*
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.ui.anime.AnimeActivity
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.visible
import java.util.*

class ListAdapter(
    private val listType: ListType,
    private val viewModel: ListsViewModel,
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<AnimeRate>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<AnimeRate>?) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list!![pos])
    }

    inner class ViewHolder(
        private val binding: ItemAnimeListBinding,
    ) : BaseViewHolder<AnimeRate>(binding.root) {

        override fun bind(item: AnimeRate) {
            with(binding) {
                Glide
                    .with(image.context)
                    .load(item.anime.image)
                    .into(image)

                if (settings.isEnLocale) {
                    primaryTitle.text = item.anime.titleEn
                    secondaryTitle.text = item.anime.titleRu
                } else {
                    primaryTitle.text = item.anime.titleRu
                    secondaryTitle.text = item.anime.titleEn
                }

                if (!settings.fullTitles) {
                    primaryTitle.maxLines = 2
                    primaryTitle.ellipsize = TextUtils.TruncateAt.END
                    secondaryTitle.maxLines = 2
                    secondaryTitle.ellipsize = TextUtils.TruncateAt.END
                }

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

                if (settings.showActions && item.anime.episodes != null) {
                    with(btnAction) {
                        when (listType) {
                            PLANNED -> {
                                setIcon(R.drawable.ic_play_filled)
                                setOnClickListener {
                                    viewModel.setList(item.id, item, PLANNED, WATCHING)
                                }
                            }
                            WATCHING -> {
                                setIcon(R.drawable.ic_check)
                                setOnClickListener {
                                    viewModel.setList(item.id, item, WATCHING, COMPLETED)
                                }
                            }
                            REWATCHING -> {
                                setIcon(R.drawable.ic_check)
                                setOnClickListener {
                                    viewModel.setList(item.id, item, REWATCHING, COMPLETED)
                                }
                            }
                            COMPLETED -> {
                                setIcon(R.drawable.ic_arrowpath)
                                setOnClickListener {
                                    viewModel.setList(item.id, item, COMPLETED, REWATCHING)
                                }
                            }
                            ON_HOLD -> {
                                setIcon(R.drawable.ic_play_filled)
                                setOnClickListener {
                                    viewModel.setList(item.id, item, ON_HOLD, WATCHING)
                                }
                            }
                            DROPPED -> {
                                setIcon(R.drawable.ic_play_filled)
                                setOnClickListener {
                                    viewModel.setList(item.id, item, DROPPED, WATCHING)
                                }
                            }
                        }
                        btnAction.visible()
                    }
                } else {
                    btnAction.gone()
                }

                root.setOnClickListener {
                    val intent = AnimeActivity.newIntent(root.context, item.anime.id)
                    root.context.startActivity(intent)
                }
            }
        }

        private fun Button.setIcon(@DrawableRes id: Int) {
            setCompoundDrawablesWithIntrinsicBounds(
                0, 0, id, 0
            )
        }
    }
}