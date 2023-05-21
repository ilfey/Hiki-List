package com.ilfey.shikimori.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ilfey.shikimori.base.ListFragment
import com.ilfey.shikimori.di.network.models.Favorites
import com.ilfey.shikimori.ui.anime.AnimeActivity
import com.ilfey.shikimori.ui.favorites.FavoritesFragment.Companion.FavoriteTypes.*
import com.ilfey.shikimori.utils.*
import com.ilfey.shikimori.utils.widgets.GridSpaceItemDecorator
import com.ilfey.shikimori.utils.widgets.HorizontalSpaceItemDecorator
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoritesFragment : ListFragment() {
    override val viewModel by activityViewModel<FavoritesViewModel>()
    override val isRefreshEnabled: Boolean = true

    private var listAdapter = ListAdapter { toast() }
    private val typeString by stringArgument(ARG_TYPE)
    private val type: FavoriteTypes
        get() = valueOf(typeString ?: ANIME.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (type == ANIME) {
            listAdapter = ListAdapter {
                val intent = AnimeActivity.newIntent(requireContext(), it.id)
                startActivity(intent)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
            addItemDecoration(
                GridSpaceItemDecorator(2, context.dp(16))
            )
        }
    }

    override fun bindViewModel() {
        viewModel.loadingFlow.launchAndCollectIn(this) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }

        when (type) {
            ANIME -> viewModel.animes.observe(viewLifecycleOwner, this::onListUpdate)
            MANGA -> viewModel.mangas.observe(viewLifecycleOwner, this::onListUpdate)
            RANOBE -> viewModel.ranobe.observe(viewLifecycleOwner, this::onListUpdate)
            CHARACTERS -> viewModel.characters.observe(viewLifecycleOwner, this::onListUpdate)
            PEOPLE -> viewModel.people.observe(viewLifecycleOwner, this::onListUpdate)
            MANGAKAS -> viewModel.mangakas.observe(viewLifecycleOwner, this::onListUpdate)
            SEYU -> viewModel.seyu.observe(viewLifecycleOwner, this::onListUpdate)
            PRODUCERS -> viewModel.producers.observe(viewLifecycleOwner, this::onListUpdate)
        }
    }

    private fun onListUpdate(list: List<Favorites.Entry>) {
        listAdapter.setList(list)
        if (list.isEmpty()) {
            binding.textViewListIsEmpty.visible()
        } else {
            binding.textViewListIsEmpty.gone()
        }
    }

    companion object {
        private const val TAG = "[FavoritesAnimeFragment]"

        private const val ARG_TYPE = "type"

        fun newInstance(type: FavoriteTypes) = FavoritesFragment().withArgs(1) {
            putString(ARG_TYPE, type.name)
        }

        enum class FavoriteTypes {
            ANIME,
            MANGA,
            RANOBE,
            CHARACTERS,
            PEOPLE,
            MANGAKAS,
            SEYU,
            PRODUCERS,
        }
    }
}