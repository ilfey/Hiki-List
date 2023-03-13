package com.ilfey.shikimori.ui.history

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilfey.shikimori.base.ListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : ListFragment() {

    override val viewModel by viewModel<HistoryViewModel>()
    override val isRefreshEnabled = true

    private var listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.history.observe(viewLifecycleOwner) {
            listAdapter.setList(it)
            binding.progress.visibility = View.GONE
        }
    }
}