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
            AnimeViewModel(settings = get(), userRateService = get(), animeService = get())
        }

        viewModel {
            AuthViewModel(repository = get(), service = get(), userService = get())
        }

        viewModel {
            HistoryViewModel(userService = get())
        }

        viewModel {
            ListsViewModel(settings = get(), animeService = get(), userService = get())
        }

        viewModel {
            FavoritesViewModel(userService = get())
        }

        viewModel {
            ProfileViewModel(settings = get(), userRateService = get(), userService = get())
        }

        viewModel {
            MainViewModel()
        }

        viewModel {
            SearchViewModel(animeService = get())
        }
    }