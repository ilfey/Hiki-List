package com.ilfey.shikimori.ui.lists

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.databinding.ItemAnimeListBinding
import com.ilfey.shikimori.databinding.ItemSearchBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.ui.anime.AnimeFragment
import com.ilfey.shikimori.utils.gone
import java.text.SimpleDateFormat
import java.util.*

class SearchListAdapter(
    private val fragment: Fragment,
    private var list: List<AnimeItem>?,
    private val showFullTitles: Boolean,
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

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
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        private val dateFormat = SimpleDateFormat(
            "dd MMMM yyyy",
            context.resources.configuration.locales.get(0),
        )

        fun bind(item: AnimeItem) {
            with(binding) {
                Glide
                    .with(image.context)
                    .load(BuildConfig.APP_URL + item.image.original)
                    .into(image)

                title.text = item.russian
                if (!showFullTitles) {
                    title.maxLines = 2
                    title.ellipsize = TextUtils.TruncateAt.END
                }

                name.text = item.name

                status.text = parseStatus(
                    item.status,
                    item.aired_on,
                    item.released_on
                )

                if (item.score.toFloat() != 0f) {
                    score.rating = item.score.toFloat() / 2
                } else {
                    score.gone()
                }

                if (item.episodes != 0) {
                    episodes.text = if (item.status == RELEASED) {
                        context.getString(R.string.episodes, item.episodes)
                    } else {
                        context.getString(R.string.episodes_of, item.episodes_aired, item.episodes)
                    }
                } else {
                    episodes.gone()
                }

                root.setOnClickListener {
                    fragment.parentFragmentManager.commit {
                        add(R.id.container, AnimeFragment.newInstance(item.id))
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