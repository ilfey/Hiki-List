package com.ilfey.shikimori.ui.favorites

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ilfey.shikimori.R

class FavoritesTabDelegate(
    private val activity: FavoritesActivity,
    private val tabLayout: TabLayout,
    private val pager: ViewPager2,
): TabLayout.OnTabSelectedListener {
    init {
        pager.adapter = PagerAdapter(activity.supportFragmentManager, activity.lifecycle)

        tabLayout.addOnTabSelectedListener(this)
        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            when (pos) {
                0 -> tab.create(R.string.anime)
                1 -> tab.create(R.string.manga)
                2 -> tab.create(R.string.ranobe)
                3 -> tab.create(R.string.characters)
                4 -> tab.create(R.string.people)
                5 -> tab.create(R.string.mangakas)
                6 -> tab.create(R.string.seyu)
                7 -> tab.create(R.string.producers)
                else -> tab.text = "No title"
            }
        }.attach()
    }

    private fun TabLayout.Tab.create(@StringRes title: Int/*, @DrawableRes icon: Int*/) {
        text = tabLayout.context.getString(title)
//        setIcon(icon)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {}
    override fun onTabUnselected(tab: TabLayout.Tab) {}
    override fun onTabReselected(tab: TabLayout.Tab) {}
}