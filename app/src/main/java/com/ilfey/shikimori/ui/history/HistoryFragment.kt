package com.ilfey.shikimori.ui.history

import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.ListFragment
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : ListFragment() {

    override val viewModel by viewModel<HistoryViewModel>()
    override val isRefreshEnabled = true

    override fun bindViewModel() {
        viewModel.history.observe(viewLifecycleOwner, this::onHistoryUpdate)
    }

    private fun onHistoryUpdate(history: List<HistoryItem>) {
        with(binding.recycler) {
            if (adapter == null) {
                adapter = ListAdapter(this@HistoryFragment)
                addItemDecoration(
                    VerticalSpaceItemDecorator(
                        resources.getDimensionPixelOffset(R.dimen.container_padding)
                    )
                )
            }
            (adapter as ListAdapter).setList(history)
        }

        binding.progress.gone()
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}