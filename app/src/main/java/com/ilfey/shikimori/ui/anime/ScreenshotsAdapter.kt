package com.ilfey.shikimori.ui.anime

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.ilfey.shikimori.R
import com.ilfey.shikimori.ui.anime.screenshots.ScreenshotsFragment
import com.ilfey.shikimori.utils.dp


class ScreenshotsAdapter(
    private val fragment: AnimeFragment,
) : RecyclerView.Adapter<ScreenshotsAdapter.ViewHolder>() {

    private var list = listOf<String>()
    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<String>) {
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
        fun bind(item: String, position: Int) {
            Glide
                .with(img)
                .load(item)
                .into(img)

            img.setOnClickListener {
                fragment.parentFragmentManager.commit {
                    replace(R.id.container, ScreenshotsFragment.newInstance(position))
                    addToBackStack(null)
                }
            }
        }
    }
}