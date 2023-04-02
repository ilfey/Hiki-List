package com.ilfey.shikimori.ui.history

import android.os.Bundle
import android.view.View
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.ListFragment
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : ListFragment() {

    override val viewModel by viewModel<HistoryViewModel>()
    override val isRefreshEnabled = true

    private val adapter = ListAdapter(this, settings.fullTitles)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recycler.adapter = adapter
            recycler.addItemDecoration(
                VerticalSpaceItemDecorator(
                    resources.getDimensionPixelOffset(R.dimen.container_padding)
                )
            )
        }
    }
    override fun bindViewModel() {
        viewModel.history.observe(viewLifecycleOwner, this::onHistoryUpdate)
    }

    private fun onHistoryUpdate(history: List<HistoryItem>) {
        adapter.setList(history)
        binding.progress.gone()
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}