package com.ilfey.shikimori.ui.lists

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ilfey.shikimori.di.network.enums.ListType.*

class PagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        ListFragment.newInstance(PLANNED),
        ListFragment.newInstance(WATCHING),
        ListFragment.newInstance(COMPLETED),
        ListFragment.newInstance(REWATCHING),
        ListFragment.newInstance(ON_HOLD),
        ListFragment.newInstance(DROPPED),
    )

    override fun getItemCount() = fragments.size // 6

    override fun createFragment(position: Int) = fragments[position]
}