package com.ilfey.shikimori.ui

import com.ilfey.shikimori.ui.anime.AnimeViewModel
import com.ilfey.shikimori.ui.auth.AuthViewModel
import com.ilfey.shikimori.ui.favorites.FavoritesViewModel
import com.ilfey.shikimori.ui.history.HistoryViewModel
import com.ilfey.shikimori.ui.lists.ListsViewModel
import com.ilfey.shikimori.ui.profile.ProfileViewModel
import com.ilfey.shikimori.ui.main.MainViewModel
import com.ilfey.shikimori.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule
    get() = module {
        viewModel {
            AnimeViewModel(settings = get(), repository = get())
        }

        viewModel {
            AuthViewModel(repository = get(), service = get())
        }

        viewModel {
            HistoryViewModel(repository = get())
        }

        viewModel {
            ListsViewModel(settings = get(), repository = get())
        }

        viewModel {
            FavoritesViewModel(repository = get())
        }

        viewModel {
            ProfileViewModel(settings = get(), repository = get())
        }

        viewModel {
            MainViewModel(settings = get(), repository = get())
        }

        viewModel {
            SearchViewModel(settings = get(), repository = get())
        }
    }