package com.ilfey.shikimori.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseActivity
import com.ilfey.shikimori.databinding.ActivityHistoryBinding
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.utils.addBackButton
import com.ilfey.shikimori.utils.launchAndCollectIn
import com.ilfey.shikimori.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {

    private val viewModel by viewModel<HistoryViewModel>()

    private var username: String? = settings.username

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.data
        if (url != null) {
            Log.d(TAG, "onCreate: start with url: $url")
            username = url.pathSegments[url.pathSegments.size -2]
        } else {
            Log.d(TAG, "onCreate: start with username: $username")
        }

        binding.toolbar.run {
            addBackButton { onBackPressedDispatcher.onBackPressed() }
            title = getString(R.string.user_history, username)
        }

        if (username != null) {
            viewModel.getHistory(username!!)
        } else {
            binding.loadingError.visible()
        }
    }

    override fun bindViewModel() {
        viewModel.history.observe(this, this::onHistoryUpdate)
        viewModel.loadingFlow.launchAndCollectIn(this) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }
    }

    private fun onHistoryUpdate(list: List<HistoryItem>) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, HistoryFragment.newInstance())
        }
    }

    override fun onInflateView() = ActivityHistoryBinding.inflate(layoutInflater)

    companion object {
        private const val TAG = "[HistoryActivity]"

        fun newIntent(context: Context) = Intent(context, HistoryActivity::class.java)
    }
}