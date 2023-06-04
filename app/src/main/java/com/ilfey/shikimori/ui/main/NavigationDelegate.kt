package com.ilfey.shikimori.ui.main

import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.ui.lists.ListsFragment
import com.ilfey.shikimori.ui.profile.ProfileFragment
import com.ilfey.shikimori.ui.search.SearchFragment

private const val TAG_MAIN = "main"

class NavigationDelegate(
    private val navBar: NavigationBarView,
    private val fragmentManager: FragmentManager,
    private val settings: AppSettings,
) : OnBackPressedCallback(false), NavigationBarView.OnItemSelectedListener,
    NavigationBarView.OnItemReselectedListener {

    init {
        navBar.setOnItemSelectedListener(this)
        navBar.setOnItemReselectedListener(this)
        navBar.selectedItemId = settings.fragment
        onNavigationItemSelected(settings.fragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return onNavigationItemSelected(item.itemId)
    }

    override fun handleOnBackPressed() {
//        navBar.selectedItemId =
    }

    private fun onNavigationItemSelected(@IdRes itemId: Int): Boolean {
        setFragment(
            when (itemId) {
                R.id.nav_lists -> ListsFragment.newInstance()
                R.id.nav_search -> SearchFragment.newInstance()
                R.id.nav_profile -> ProfileFragment.newInstance(settings.username!!)
                else -> return false
            },
        )
        settings.fragment = itemId
        return true
    }

    private fun setFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.nav_container, fragment, TAG_MAIN)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }.commit()
    }

    override fun onNavigationItemReselected(item: MenuItem) {}
}