package com.ilfey.shikimori.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentListBinding
import com.ilfey.shikimori.utils.getThemeColor

abstract class ListFragment : BaseFragment<FragmentListBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    abstract val viewModel: ListViewModel
    abstract val isRefreshEnabling: Boolean

    var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager = layoutManager

        with(binding.refresh) {
            setProgressBackgroundColorSchemeColor(context.getThemeColor(com.google.android.material.R.attr.colorPrimary))
            setColorSchemeColors(context.getThemeColor(com.google.android.material.R.attr.colorOnPrimary))
            setOnRefreshListener(this@ListFragment)
            isEnabled = this@ListFragment.isRefreshEnabling
        }
    }

    @CallSuper
    override fun onRefresh() {
        binding.refresh.isRefreshing = true
        viewModel.onRefresh()
        binding.refresh.isRefreshing = false
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentListBinding.inflate(inflater, container, false)
}