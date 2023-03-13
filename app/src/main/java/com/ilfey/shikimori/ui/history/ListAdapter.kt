package com.ilfey.shikimori.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.databinding.ItemHistoryBinding
import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.AnimeStatus.*
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.utils.toast
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<HistoryItem> = listOf()
    private val parseFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<HistoryItem>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.context,
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    inner class ViewHolder(
        private val context: Context,
        private val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat(
            "dd MMMM yyyy",
            context.resources.configuration.locales.get(0),
        )

        fun bind(item: HistoryItem) {
            binding.description.text = Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)

            if (item.target == null) {
                binding.title.visibility = View.GONE
                binding.image.visibility = View.GONE
                binding.name.visibility = View.GONE
                binding.score.visibility = View.GONE
                binding.status.visibility = View.GONE
            } else {
                binding.title.text = item.target.russian
                binding.name.text = item.target.name

                item.target.score.toFloat().let { score ->
                    if (score != 0f) {
                        binding.score.rating = score / 2
                    } else {
                        binding.score.visibility = View.GONE
                    }
                }

                binding.status.text = parseStatus(
                    item.target.status,
                    item.target.aired_on,
                    item.target.released_on
                )

                Glide
                    .with(binding.image.context)
                    .load(BuildConfig.APP_URL + item.target.image.original)
                    .into(binding.image)

                binding.root.setOnClickListener {
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
                        binding.root.findNavController().navigate(
                            HistoryFragmentDirections.actionHistoryFragmentToAnimeFragment(item.target.id)
                        )
                    }
                }
            }
        }

        private fun parseStatus(status: AnimeStatus, aired_on: String?, released_on: String?) =
            when (status) {
                ANONS -> {
                    val date = aired_on?.let {
                        parseFormat.parse(
                            it
                        )
                    }

                    if (date != null) {
                        context.getString(R.string.anons_for, dateFormat.format(date))
                    } else {
                        context.getString(R.string.anons)
                    }
                }
                ONGOING -> {
                    val date = aired_on?.let {
                        parseFormat.parse(
                            it
                        )
                    }

                    if (date != null) {
                        context.getString(R.string.ongoing_from, dateFormat.format(date))
                    } else {
                        context.getString(R.string.ongoing)
                    }
                }
                RELEASED -> {
                    val date = released_on?.let {
                        parseFormat.parse(
                            it
                        )
                    }

                    if (date != null) {
                        context.getString(R.string.released_on, dateFormat.format(date))
                    } else {
                        context.getString(R.string.released)
                    }
                }
            }
    }
}