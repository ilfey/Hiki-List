package com.ilfey.shikimori.ui.lists

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.ListType.*
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.isVisible
import com.ilfey.shikimori.utils.launchAndCollectIn
import com.ilfey.shikimori.utils.visible
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator

class SearchInTabDelegate(
    private val fragment: Fragment,
    private val searchBar: SearchBar,
    private val tabLayout: TabLayout,
    pager: ViewPager2,
    private val searchView: SearchView,
    private val settings: AppSettings,
    private val viewModel: ListsViewModel,
) : TabLayout.OnTabSelectedListener, SearchView.TransitionListener, TextWatcher {

    private val adapter = SearchListAdapter(null)

    private val textViewNotFound = searchView.findViewById<TextView>(R.id.textView_not_found_search)
    private val progressBar =
        searchView.findViewById<ContentLoadingProgressBar>(R.id.progressBar_search)
    private val recyclerView = fragment.view?.findViewById<RecyclerView>(R.id.recyclerView_search)

    private var currentList: ListType
        get() = settings.list ?: PLANNED
        set(value) {
            settings.list = value
        }

    init {
        pager.offscreenPageLimit = 6
        pager.adapter = PagerAdapter(fragment)
        pager.setCurrentItem(
            when (currentList) {
                PLANNED -> 0
                WATCHING -> 1
                COMPLETED -> 2
                REWATCHING -> 3
                ON_HOLD -> 4
                DROPPED -> 5
            }, false
        )

        tabLayout.addOnTabSelectedListener(this)
//        tabLayout.setupWithViewPager(pager)
        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            when (pos) {
                0 -> tab.create(R.string.planned, R.drawable.ic_clock)
                1 -> tab.create(R.string.watching, R.drawable.ic_play)
                2 -> tab.create(R.string.completed, R.drawable.ic_check)
                3 -> tab.create(R.string.rewatching, R.drawable.ic_arrowpath)
                4 -> tab.create(R.string.on_hold, R.drawable.ic_inbox)
                5 -> tab.create(R.string.dropped, R.drawable.ic_trash)
                else -> tab.text = "No title"
            }
        }.attach()

        searchView.addTransitionListener(this)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(
            VerticalSpaceItemDecorator(
                fragment.resources.getDimensionPixelOffset(R.dimen.container_padding),
            )
        )

        viewModel.searchList.observe(fragment.viewLifecycleOwner, this::onAnimeUpdate)
        viewModel.loadingFlow.launchAndCollectIn(fragment.viewLifecycleOwner) {
            if (it) {
                textViewNotFound.isVisible { view ->
                    view.gone()
                }
                progressBar.visible()
            } else {
                progressBar.gone()
            }
        }
    }

    private fun onAnimeUpdate(list: List<AnimeItem>) {
        adapter.setList(list)
        if (list.isEmpty()) {
            textViewNotFound.visible()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        currentList = when (tab.position) {
            0 -> PLANNED
            1 -> WATCHING
            2 -> COMPLETED
            3 -> REWATCHING
            4 -> ON_HOLD
            5 -> DROPPED
            else -> PLANNED
        }
        viewModel.lastStatus = currentList
        searchBar.hint = fragment.getString(R.string.search_in, tab.text)
        searchView.hint = fragment.getString(R.string.search_in, tab.text)
    }

    override fun onStateChanged(
        searchView: SearchView,
        previousState: SearchView.TransitionState,
        newState: SearchView.TransitionState
    ) = when (newState) {
        SearchView.TransitionState.SHOWING -> searchView.editText.addTextChangedListener(this)
        else -> adapter.setList(null)
    }

    override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
//        if ((text?.length ?: 0) > 2) {
        if (text.isNotEmpty()) {
            viewModel.searchInList(currentList, text.toString())
        }
//        }
    }

//    private fun createTab(@StringRes title: Int, @DrawableRes icon: Int) {
//        val tab = tabLayout.newTab()
//        tab.text = fragment.getString(title)
//        tab.setIcon(icon)
//        tabLayout.addTab(tab)
//    }
    private fun TabLayout.Tab.create(@StringRes title: Int, @DrawableRes icon: Int) {
        text = tabLayout.context.getString(title)
        if (settings.showIcons) {
            setIcon(icon)
        }
    }

    override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(p0: Editable) {}
    override fun onTabUnselected(tab: TabLayout.Tab) {}
    override fun onTabReselected(tab: TabLayout.Tab) {}
}