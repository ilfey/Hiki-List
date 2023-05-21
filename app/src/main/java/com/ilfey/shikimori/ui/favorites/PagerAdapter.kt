package com.ilfey.shikimori.ui.favorites

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ilfey.shikimori.ui.favorites.FavoritesFragment.Companion.FavoriteTypes.*

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle){

    private val fragments = listOf(
        FavoritesFragment.newInstance(ANIME),
        FavoritesFragment.newInstance(MANGA),
        FavoritesFragment.newInstance(RANOBE),
        FavoritesFragment.newInstance(CHARACTERS),
        FavoritesFragment.newInstance(PEOPLE),
        FavoritesFragment.newInstance(MANGAKAS),
        FavoritesFragment.newInstance(SEYU),
        FavoritesFragment.newInstance(PRODUCERS),
    )

    override fun getItemCount() = fragments.size // 8

    override fun createFragment(position: Int) = fragments[position]
}