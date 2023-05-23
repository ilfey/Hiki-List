package com.ilfey.shikimori.ui.news

import android.os.Bundle
import android.util.Log
import android.view.View
import com.ilfey.shikimori.base.ListFragment
import com.ilfey.shikimori.di.network.models.Message
import com.ilfey.shikimori.utils.dp
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.launchAndCollectIn
import com.ilfey.shikimori.utils.visible
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : ListFragment() {
    override val isRefreshEnabled = true
    override val viewModel by viewModel<NewsViewModel>()

    private val listAdapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(settings.username != null) {
            viewModel.getNews(settings.username!!)
        } else {
//            TODO: handle error
            Log.d(TAG, "onCreate: username cannot be null")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler) {
            adapter = listAdapter
            addItemDecoration(
                VerticalSpaceItemDecorator(context.dp(16))
            )
        }
    }

    override fun bindViewModel() {
        viewModel.news.observe(viewLifecycleOwner, this::onNewsUpdate)
        viewModel.loadingFlow.launchAndCollectIn(this) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }
    }

    private fun onNewsUpdate(news: List<Message>) {
        listAdapter.setList(news)
        if (news.isEmpty()) {
            binding.textViewListIsEmpty.visible()
        } else {
            binding.textViewListIsEmpty.gone()
        }
    }

    companion object {
        private const val TAG = "[NewsFragment]"

        fun newInstance() = NewsFragment()
    }
}