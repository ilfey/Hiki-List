package com.ilfey.shikimori.ui.lists

import android.os.Bundle
import android.view.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentListsBinding
import com.ilfey.shikimori.di.network.enums.ListType
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ListsFragment : BaseFragment<FragmentListsBinding>() {

    private val viewModel by activityViewModel<ListsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
//            toolbar.run {
//                inflateMenu(R.menu.menu_lists)
//            }
            SearchInTabDelegate(this@ListsFragment, searchBar, tabLayout, pager, searchView, settings, viewModel)
        }
    }

    override fun bindViewModel() {
        viewModel.moveAnime.observe(viewLifecycleOwner, this::onAnimeMove)
    }

    private fun onAnimeMove(move: ListsViewModel.MoveAnime?) {
        if (move != null) {
            val listTitle = when (move.toList) {
                ListType.PLANNED -> getString(R.string.planned)
                ListType.WATCHING -> getString(R.string.watching)
                ListType.COMPLETED -> getString(R.string.completed)
                ListType.REWATCHING -> getString(R.string.rewatching)
                ListType.ON_HOLD -> getString(R.string.on_hold)
                ListType.DROPPED -> getString(R.string.dropped)
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
    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListsBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = ListsFragment()
    }
}