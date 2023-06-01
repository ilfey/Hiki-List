package com.ilfey.shikimori.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentHistoryBinding
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.utils.getThemeColor
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by activityViewModel<HistoryViewModel>()

    private val adapter = ListAdapter()
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

        with(binding.refresh) {
            setProgressBackgroundColorSchemeColor(context.getThemeColor(com.google.android.material.R.attr.colorPrimary))
            setColorSchemeColors(context.getThemeColor(com.google.android.material.R.attr.colorOnPrimary))
            setOnRefreshListener(this@HistoryFragment)
        }
    }

    override fun bindViewModel() {
        viewModel.history.observe(viewLifecycleOwner, this::onHistoryUpdate)
    }

    private fun onHistoryUpdate(history: List<HistoryItem>) {
        adapter.setList(history)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHistoryBinding.inflate(layoutInflater, container, false)


    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onRefresh() {
        binding.refresh.isRefreshing = true
        viewModel.onRefresh()
        binding.refresh.isRefreshing = false
    }
}