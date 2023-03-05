package com.ilfey.shikimori.ui.favorites

import android.os.Bundle
import android.view.View
import com.ilfey.shikimori.base.ListFragment
import org.koin.android.ext.android.inject

class FavoritesFragment : ListFragment() {
    override val viewModel by inject<FavoritesViewModel>()
    override val isRefreshEnabled = true

    private var listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = listAdapter
    }

    override fun bindViewModel() {

        viewModel.favorites.observe(viewLifecycleOwner) {
//            val list = arrayListOf<Favourites.Entry>()
//
//            list.addAll(it.animes)
//            list.addAll(it.mangas)
//            list.addAll(it.ranobe)
//            list.addAll(it.characters)
//            list.addAll(it.people)
//            list.addAll(it.mangakas)
//            list.addAll(it.seyu)
//            list.addAll(it.producers)

            listAdapter.setList(it.animes)
            binding.progress.visibility = View.GONE
        }
    }
}