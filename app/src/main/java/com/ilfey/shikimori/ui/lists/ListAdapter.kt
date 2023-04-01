package com.ilfey.shikimori.ui.lists

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.databinding.ItemAnimeListBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.ui.anime.AnimeFragment
import com.ilfey.shikimori.utils.gone
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(
    private val fragment: Fragment,
    var _list: List<AnimeRate>?,
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
                    .load(BuildConfig.APP_URL + item.anime.image.original)
                    .into(image)

                title.text = item.anime.russian
                name.text = item.anime.name

                if (item.score != 0) {
                    userScore.text =
                        binding.userScore.context.getString(
                            R.string.your_score_with_score,
                            item.score
                        )
                } else {
                    userScore.gone()
                }

                status.text = parseStatus(
                    item.anime.status,
                    item.anime.aired_on,
                    item.anime.released_on
                )

                if (item.anime.score.toFloat() != 0f) {
                    score.rating = item.anime.score.toFloat() / 2
                } else {
                    score.gone()
                }

                /* YandereDev reference */

                if (item.anime.episodes != 0) {
                    episodes.text = if (item.episodes != 0) {
                        if (item.anime.status == RELEASED) {
                            context.getString(
                                R.string.episodes_with_count,
                                item.episodes,
                                item.anime.episodes,
                            )
                        } else {
                            context.getString(
                                R.string.episodes_of_with_count,
                                item.episodes,
                                item.anime.episodes_aired,
                                item.anime.episodes,
                            )
                        }
                    } else {
                        if (item.anime.status == RELEASED) {
                            context.getString(
                                R.string.episodes,
                                item.anime.episodes,
                            )
                        } else {
                            context.getString(
                                R.string.episodes_of,
                                item.anime.episodes_aired,
                                item.anime.episodes
                            )
                        }
                    }
                } else {
                    if (item.anime.status == ONGOING) {
                        episodes.text = if (item.episodes != 0) {
                            context.getString(
                                R.string.episodes_of_with_null,
                                item.episodes,
                                item.anime.episodes_aired,
                            )
                        } else {
                            context.getString(
                                R.string.episodes_of_null,
                                item.anime.episodes_aired,
                            )
                        }
                    } else {
                        episodes.gone()
                    }
                }

                root.setOnClickListener {
                    fragment.parentFragmentManager.commit {
                        add(R.id.container, AnimeFragment.newInstance(item.anime.id))
                        addToBackStack(null)
                    }
                }
            }
        }

        private fun parseStatus(status: AnimeStatus, aired_on: Date?, released_on: Date?) =
            when (status) {
                ANONS -> {
                    if (aired_on != null) {
                        context.getString(R.string.anons_for, dateFormat.format(aired_on))
                    } else {
                        context.getString(R.string.anons)
                    }
                }
                ONGOING -> {
                    if (aired_on != null) {
                        context.getString(R.string.ongoing_from, dateFormat.format(aired_on))
                    } else {
                        context.getString(R.string.ongoing)
                    }
                }
                RELEASED -> {
                    if (released_on != null) {
                        context.getString(R.string.released_on, dateFormat.format(released_on))
                    } else {
                        context.getString(R.string.released)
                    }
                }
            }
    }
}