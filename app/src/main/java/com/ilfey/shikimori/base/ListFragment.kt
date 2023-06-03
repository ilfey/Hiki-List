package com.ilfey.shikimori.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilfey.shikimori.databinding.FragmentListBinding
import com.ilfey.shikimori.utils.getThemeColor
import com.google.android.material.R as mR

abstract class ListFragment : BaseFragment<FragmentListBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    abstract val viewModel: ListViewModel
    abstract val isRefreshEnabled: Boolean

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.refresh) {
            setProgressBackgroundColorSchemeColor(context.getThemeColor(mR.attr.colorPrimary))
            setColorSchemeColors(context.getThemeColor(mR.attr.colorOnPrimary))
            setOnRefreshListener(this@ListFragment)
            isEnabled = this@ListFragment.isRefreshEnabled
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