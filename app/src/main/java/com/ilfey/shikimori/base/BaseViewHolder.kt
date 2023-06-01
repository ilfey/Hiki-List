package com.ilfey.shikimori.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ilfey.shikimori.di.AppSettings
import org.koin.java.KoinJavaComponent.inject

abstract class BaseViewHolder<B>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val settings by inject<AppSettings>(AppSettings::class.java)

    abstract fun bind(item: B)
}