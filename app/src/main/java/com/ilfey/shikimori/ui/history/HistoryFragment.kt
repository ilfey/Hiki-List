package com.ilfey.shikimori.ui.history

import android.os.Bundle
import android.view.View
import com.ilfey.shikimori.base.ListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : ListFragment() {

    override val viewModel by viewModel<HistoryViewModel>()
    override val isRefreshEnabling = true

    private var listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = listAdapter
    }

    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.history.observe(viewLifecycleOwner) {
            listAdapter.setList(it)
            binding.progress.visibility = View.GONE
        }
    }
}