package com.ilfey.shikimori.ui.lists

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.ListType.*
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.utils.*
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ListFragment : com.ilfey.shikimori.base.ListFragment() {
    override val viewModel by activityViewModel<ListsViewModel>()
    override val isRefreshEnabled = true

    val list: ListType
        get() = stringArgument(ARG_LIST_TYPE).value?.let { enumValueOf<ListType>(it) } ?: PLANNED

    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = arguments?.getString(ARG_LIST_TYPE)?.let { enumValueOf(it) } ?: PLANNED

        adapter = ListAdapter(list, viewModel)

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
        when (list) {
            PLANNED -> viewModel.planned.observe(viewLifecycleOwner, this::onListUpdate)
            WATCHING -> viewModel.watching.observe(viewLifecycleOwner, this::onListUpdate)
            REWATCHING -> viewModel.rewatching.observe(viewLifecycleOwner, this::onListUpdate)
            COMPLETED -> viewModel.completed.observe(viewLifecycleOwner, this::onListUpdate)
            ON_HOLD -> viewModel.on_hold.observe(viewLifecycleOwner, this::onListUpdate)
            DROPPED -> viewModel.dropped.observe(viewLifecycleOwner, this::onListUpdate)
        }

        viewModel.moveAnime.observe(viewLifecycleOwner, this::onAnimeMove)
        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    textViewListIsEmpty.isVisible { view ->
                        view.gone()
                    }
                    progressBar.show()
                } else {
                    progressBar.hide()
                }
            }
        }
    }

    private fun onAnimeMove(move: ListsViewModel.MoveAnime?) {
        if (move != null) {
            val listTitle = when (move.toList) {
                PLANNED -> getString(R.string.planned)
                WATCHING -> getString(R.string.watching)
                REWATCHING -> getString(R.string.rewatching)
                COMPLETED -> getString(R.string.completed)
                ON_HOLD -> getString(R.string.on_hold)
                DROPPED -> getString(R.string.dropped)
            }

            val text = getString(R.string.move_anime_to, move.animeRate.anime.titleRu, listTitle)

            context?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.cancel) {
                        viewModel.setList(
                            move.animeRate.id,
                            move.animeRate,
                            move.toList,
                            move.fromList,
                            cancel = true,
                        )
                    }
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            viewModel.moveAnime.value = null
                        }
                    })
                    .show()
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
        private const val TAG = "[ListFragment]"

        private const val ARG_LIST_TYPE = "list_type"

        fun newInstance(
            lt: ListType?
        ) = ListFragment().withArgs(1) {
            putString(ARG_LIST_TYPE, lt?.name)
        }
    }
}