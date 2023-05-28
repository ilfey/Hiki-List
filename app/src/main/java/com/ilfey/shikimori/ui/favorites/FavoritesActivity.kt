package com.ilfey.shikimori.ui.favorites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseActivity
import com.ilfey.shikimori.databinding.ActivityFavoritesBinding
import com.ilfey.shikimori.di.network.entities.Favorites
import com.ilfey.shikimori.utils.addBackButton
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.launchAndCollectIn
import com.ilfey.shikimori.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesActivity : BaseActivity<ActivityFavoritesBinding>() {

    private val viewModel by viewModel<FavoritesViewModel>()

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
            title = getString(R.string.user_favorites, username)
        }

        FavoritesTabDelegate(this, binding.tabLayout, binding.pager)

        if (username != null) {
            viewModel.getFavorites(username!!)
        } else {
            binding.pager.gone()
            binding.loadingError.visible()
        }
    }

    override fun bindViewModel() {
        viewModel.favorites.observe(this, this::onFavoritesUpdate)
        viewModel.loadingFlow.launchAndCollectIn(this) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }
    }

    private fun onFavoritesUpdate(favorites: Favorites) {
//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            replace(R.id.container, FavoritesFragment.newInstance())
//        }
    }

    override fun onInflateView() = ActivityFavoritesBinding.inflate(layoutInflater)

    companion object {
        private const val TAG = "[FavoritesActivity]"

        fun newIntent(context: Context) = Intent(context, FavoritesActivity::class.java)
    }
}