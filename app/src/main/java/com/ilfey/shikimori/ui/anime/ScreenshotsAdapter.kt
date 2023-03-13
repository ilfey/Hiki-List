package com.ilfey.shikimori.ui.anime

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.utils.dp


class ScreenshotsAdapter(
    private val fragment: Fragment,
    private var list: List<Anime.Screenshot>
) : RecyclerView.Adapter<ScreenshotsAdapter.ViewHolder>() {

    private var screenshotsUrl = list.map {
        it.original
    }.toTypedArray()

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Anime.Screenshot>) {
        list = l

        screenshotsUrl = list.map {
            it.original
        }.toTypedArray()

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ctx = parent.context
        val img = ImageView(ctx)
//        img.scaleType = ImageView.ScaleType.CENTER_INSIDE
        img.layoutParams = ViewGroup.LayoutParams(ctx.dp(300), ctx.dp(200))

        return ViewHolder(img)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class ViewHolder(
//        private val ctx: Context,
        private val img: ImageView,
    ) : RecyclerView.ViewHolder(img) {
        fun bind(item: Anime.Screenshot, position: Int) {

            img.setOnClickListener {
                findNavController(fragment).navigate(
                    AnimeFragmentDirections.actionAnimeFragmentToScreenshotsFragment(
                        screenshotsUrl, position
                    )
                )
            }

            Glide
                .with(img)
                .load(BuildConfig.APP_URL + item.preview)
                .into(img)
        }
    }
}