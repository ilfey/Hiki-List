package com.ilfey.shikimori.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.search.SearchView
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentSearchBinding
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.isVisible
import com.ilfey.shikimori.utils.launchAndCollectIn
import com.ilfey.shikimori.utils.visible
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(), SearchView.TransitionListener {

    private val viewModel by viewModel<SearchViewModel>()
    private val adapter = ListAdapter(null, settings.fullTitles)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.search(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.addTransitionListener(this@SearchFragment)
            recyclerViewSearch.addItemDecoration(
                VerticalSpaceItemDecorator(
                    resources.getDimensionPixelOffset(R.dimen.container_padding),
                )
            )

            recyclerViewList.adapter = adapter
            recyclerViewList.addItemDecoration(
                VerticalSpaceItemDecorator(
                    resources.getDimensionPixelOffset(R.dimen.container_padding),
                )
            )
        }
    }

    override fun bindViewModel() {
        viewModel.animes.observe(viewLifecycleOwner, this::onAnimeUpdate)
        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    textViewNotFound.isVisible { view ->
                        view.gone()
                    }
                    textViewNotFoundSearch.isVisible { view ->
                        view.gone()
                    }
                    progressBar.visible(progressBarSearch)
                } else {
                    progressBar.gone(progressBarSearch)
                }
            }
        }
    }

    private fun onAnimeUpdate(animes: List<AnimeItem>) {
        with(binding) {
            adapter.setList(animes)
            if (animes.isEmpty()) {
                textViewNotFound.visible(textViewNotFoundSearch)
            }
            if (recyclerViewSearch.adapter != null) {
                (recyclerViewSearch.adapter as ListAdapter).setList(animes)
            }
        }
    }

    override fun onStateChanged(
        searchView: SearchView,
        previousState: SearchView.TransitionState,
        newState: SearchView.TransitionState
    ) {
        val adapter = ListAdapter(null, settings.fullTitles)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                if ((text?.length ?: 0) > 2) {
                    viewModel.search(text.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        when (newState) {
            SearchView.TransitionState.SHOWING -> {
                binding.searchView.editText.addTextChangedListener(textWatcher)
                binding.recyclerViewSearch.adapter = adapter
            }
            SearchView.TransitionState.HIDING -> {
                binding.recyclerViewSearch.adapter = null
                binding.searchView.editText.removeTextChangedListener(textWatcher)
            }
            else -> {}
        }
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = SearchFragment()
    }
}