package com.ilfey.shikimori.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.databinding.ItemHistoryBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.ui.anime.AnimeFragment
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.toast
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(
    private val fragment: Fragment,
    private val showFullTitles: Boolean,
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<HistoryItem> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<HistoryItem>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    inner class ViewHolder(
        private val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context
        private val dateFormat = SimpleDateFormat(
            "dd MMMM yyyy",
            context.resources.configuration.locales.get(0),
        )

        fun bind(item: HistoryItem) {
            with(binding) {
                description.text =
                    Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)

                if (item.target == null) {
                    title.gone()
                    image.gone()
                    name.gone()
                    score.gone()
                    status.gone()
                } else {
                    title.text = item.target.russian
                    if (!showFullTitles) {
                        title.maxLines = 2
                        title.ellipsize = TextUtils.TruncateAt.END
                    }

                    name.text = item.target.name

                    item.target.score.toFloat().let { score ->
                        if (score != 0f) {
                            binding.score.rating = score / 2
                        } else {
                            binding.score.gone()
                        }
                    }

                    binding.status.text = parseStatus(
                        item.target.status,
                        item.target.aired_on,
                        item.target.released_on
                    )

                    Glide
                        .with(image.context)
                        .load(BuildConfig.APP_URL + item.target.image.original)
                        .into(image)


                    root.setOnClickListener {
                        if (item.target.kind in arrayOf(
                                Kind.MANGA,
                                Kind.MANHWA,
                                Kind.MANHUA,
                                Kind.LIGHT_NOVEL,
                                Kind.NOVEL,
                                Kind.ONE_SHOT,
                                Kind.DOUJIN,
                            )
                        ) {
                            // TODO: Implement this
                            context.toast(context.getString(R.string.functionality_not_implemented_yet))
                        } else {
                            fragment.parentFragmentManager.commit {
                                add(R.id.container, AnimeFragment.newInstance(item.target.id))
                                addToBackStack(null)
                            }
                        }
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