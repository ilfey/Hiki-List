package com.ilfey.shikimori.ui.lists

import android.os.Bundle
import android.view.View
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.utils.*
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.android.ext.android.inject

class ListFragment : com.ilfey.shikimori.base.ListFragment() {
    override val viewModel: ListViewModel by inject()
    override val isRefreshEnabled = true

    private val adapter = ListAdapter(this, null, settings.fullTitles)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = arguments?.getString(ARG_LIST_TYPE)?.let { enumValueOf(it) } ?: ListTypes.PLANNED

        viewModel.getList(list)
    }

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
        viewModel.list.observe(viewLifecycleOwner, this::onListUpdate)
        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            with(binding){
                if (it) {
                    textViewListIsEmpty.isVisible { view ->
                        view.gone()
                    }
                    progress.visible()
                } else {
                    progress.gone()
                }
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

    companion object {
        private const val ARG_LIST_TYPE = "list_type"

        fun newInstance(
            lt: ListTypes?
        ) = ListFragment().withArgs(1){
            putString(ARG_LIST_TYPE, lt?.name)
        }
    }
}