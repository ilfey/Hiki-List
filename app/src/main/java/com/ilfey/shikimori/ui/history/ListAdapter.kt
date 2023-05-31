package com.ilfey.shikimori.ui.history

import android.annotation.SuppressLint
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.databinding.ItemHistoryBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.ui.anime.AnimeActivity
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.toast
import com.ilfey.shikimori.utils.visible
import java.util.*

class ListAdapter(
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

        fun bind(item: HistoryItem) {
            with(binding) {
                description.text =
                    Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)

                if (item.target == null) {
                    title.gone(image, name, score, status)
                } else {
                    title.visible(image, name, score, status)

                    Glide
                        .with(image.context)
                        .load(item.target.image)
                        .into(image)

                    title.text = item.target.titleRu
                    if (!showFullTitles) {
                        title.maxLines = 2
                        title.ellipsize = TextUtils.TruncateAt.END
                    }

                    name.text = item.target.titleEn

                    if (item.target.score != 0f) {
                        score.rating = item.target.score
                        score.visible()
                    } else {
                        score.gone()
                    }

                    status.text = item.target.status

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
                            // TODO: Implement this activity
                            context.toast()
                        } else {
                            val intent = AnimeActivity.newIntent(root.context, item.target.id)
                            root.context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}