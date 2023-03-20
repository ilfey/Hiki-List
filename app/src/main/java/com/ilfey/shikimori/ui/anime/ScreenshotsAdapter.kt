package com.ilfey.shikimori.ui.anime

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.utils.dp


class ScreenshotsAdapter(
    private val fragment: Fragment,
    private var list: List<Anime.Screenshot>,
) : RecyclerView.Adapter<ScreenshotsAdapter.ViewHolder>() {

    private val screenshotsUrl: Array<String>
        get() = list.map {
            BuildConfig.APP_URL + it.original
        }.toTypedArray()

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Anime.Screenshot>) {
        list = l

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ctx = parent.context
        val img = ShapeableImageView(ctx)
        img.scaleType = ImageView.ScaleType.CENTER_CROP
        img.layoutParams = ViewGroup.LayoutParams(ctx.dp(300), ctx.dp(200))
        img.shapeAppearanceModel = img.shapeAppearanceModel.toBuilder()
            .setAllCornerSizes(ctx.resources.getDimension(R.dimen.small_corner))
            .build()
        return ViewHolder(img)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class ViewHolder(
        private val img: ImageView,
    ) : RecyclerView.ViewHolder(img) {
        fun bind(item: Anime.Screenshot, position: Int) {
            Glide
                .with(img)
                .load(BuildConfig.APP_URL + item.original)
                .into(img)

            img.setOnClickListener {
                findNavController(fragment).navigate(
                    AnimeFragmentDirections.actionAnimeFragmentToScreenshotsFragment(
                        screenshotsUrl, position
                    )
                )
            }
        }
    }
}