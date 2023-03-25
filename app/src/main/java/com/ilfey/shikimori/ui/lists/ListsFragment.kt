package com.ilfey.shikimori.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.ChipGroup
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentListsBinding
import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.enums.ListTypes.*
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.utils.*
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.android.ext.android.inject

class ListsFragment : BaseFragment<FragmentListsBinding>(), ChipGroup.OnCheckedStateChangeListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by inject<ListsViewModel>()
    private val adapter = ListAdapter(this, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.swipeRefresh) {
            setProgressBackgroundColorSchemeColor(context.getThemeColor(com.google.android.material.R.attr.colorPrimary))
            setColorSchemeColors(context.getThemeColor(com.google.android.material.R.attr.colorOnPrimary))
            setOnRefreshListener(this@ListsFragment)
        }

        with(binding) {
            recyclerViewList.adapter = adapter
            recyclerViewList.addItemDecoration(
                VerticalSpaceItemDecorator(
                    resources.getDimensionPixelOffset(R.dimen.container_padding),
                )
            )

            chipGroupLists.setOnCheckedStateChangeListener(this@ListsFragment)
            chipGroupLists.check(
                when (settings.list ?: PLANNED) {
                    PLANNED -> R.id.chip_planned
                    WATCHING -> R.id.chip_watching
                    COMPLETED -> R.id.chip_completed
                    REWATCHING -> R.id.chip_rewatching
                    ON_HOLD -> R.id.chip_on_hold
                    DROPPED -> R.id.chip_dropped
                }
            )
        }
    }

    override fun bindViewModel() {
        viewModel.list.observe(viewLifecycleOwner, this::onListUpdate)
        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            if (it) {
                binding.textViewListIsEmpty.isVisible { view ->
                    view.gone()
                }
                binding.progressBar.visible()
            } else {
                binding.progressBar.gone()
            }
        }
    }

    private fun onListUpdate(list: List<AnimeRate>) {
        with(binding) {
            if (list.isEmpty()) {
                textViewListIsEmpty.visible()
            }
            adapter.setList(list)
        }
    }

    override fun onRefresh() {
        binding.swipeRefresh.isRefreshing = true
        adapter.setList(null)
        viewModel.onRefresh()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onCheckedChanged(group: ChipGroup, ids: MutableList<Int>) {
        when (ids[0]) {
            R.id.chip_planned -> replaceList(PLANNED)
            R.id.chip_watching -> replaceList(WATCHING)
            R.id.chip_completed -> replaceList(COMPLETED)
            R.id.chip_rewatching -> replaceList(REWATCHING)
            R.id.chip_on_hold -> replaceList(ON_HOLD)
            R.id.chip_dropped -> replaceList(DROPPED)
        }
    }

    private fun replaceList(list: ListTypes) {
        settings.list = list
        viewModel.getList(list) // -> onListUpdate()
        adapter.setList(null)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListsBinding.inflate(inflater, container, false)


    companion object {
        fun newInstance() = ListsFragment()
    }
}