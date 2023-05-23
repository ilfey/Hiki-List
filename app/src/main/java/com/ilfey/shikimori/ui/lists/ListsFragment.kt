package com.ilfey.shikimori.ui.lists

import android.os.Bundle
import android.view.*
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentListsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListsFragment : BaseFragment<FragmentListsBinding>() {

    private val viewModel by viewModel<ListsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
//            toolbar.run {
//                inflateMenu(R.menu.menu_lists)
//            }
            SearchInTabDelegate(this@ListsFragment, searchBar, tabLayout, pager, searchView, settings, viewModel)
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