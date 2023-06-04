package com.ilfey.shikimori.ui.profile.friends

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.base.BaseViewHolder
import com.ilfey.shikimori.databinding.ItemFriendsBinding
import com.ilfey.shikimori.di.network.models.Friend


class ListAdapter(
    private val onClick: (Friend) -> Unit = {},
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<Friend> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Friend>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    inner class ViewHolder(
        private val binding: ItemFriendsBinding,
    ) : BaseViewHolder<Friend>(binding.root) {

        override fun bind(item: Friend) {

            with(binding) {
                Glide
                    .with(avatar.context)
                    .load(item.image)
                    .into(avatar)

                username.text = item.username
                lastOnline.text = item.lastOnline

                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }
    }
}