package com.ilfey.shikimori.ui.anime

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseActivity
import com.ilfey.shikimori.databinding.ActivityAnimeBinding
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.ui.anime.info.InfoBottomSheet
import com.ilfey.shikimori.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeActivity : BaseActivity<ActivityAnimeBinding>(), Toolbar.OnMenuItemClickListener {

    private val viewModel by viewModel<AnimeViewModel>()
    private var id: Long = 0
    private var currentList = -1
    private var rateId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id: Long?
        val url = intent.data
        if (url == null) {
            val extra = intent.getLongExtra(EXTRA_ID, -1)
            id = if (extra == -1L) null else extra
            Log.d(TAG, "onCreate: start with id: $id")
        } else {
            val reg = Regex("\\d+")
            val idString = reg.find(url.lastPathSegment.toString())?.value
            id = idString?.toLong()
            Log.d(TAG, "onCreate: start with url: $url")
        }

        binding.toolbar.run {
            addBackButton { onBackPressedDispatcher.onBackPressed() }
            inflateMenu(R.menu.anime_toolbar_menu)
            setOnMenuItemClickListener(this@AnimeActivity)
        }

        if (id != null) {
            this.id = id
            viewModel.getAnime(id)
        } else {
            binding.container.gone()
            binding.loadingError.visible()
        }
    }

    override fun bindViewModel() {
        viewModel.anime.observe(this, this::onAnimeUpdate)
        viewModel.userRate.observe(this, this::onRateUpdate)
        viewModel.loadingFlow.launchAndCollectIn(this) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }
    }
    private fun onAnimeUpdate(anime: Anime) {
        binding.toolbar.title = anime.titleRu
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, AnimeFragment.newInstance(anime.id))
        }
    }

    private fun onRateUpdate(rate: UserRate) {
        rateId = rate.id
        currentList = when (rate.list) {
            ListType.PLANNED -> 0
            ListType.WATCHING -> 1
            ListType.REWATCHING -> 2
            ListType.COMPLETED -> 3
            ListType.ON_HOLD -> 4
            ListType.DROPPED -> 5
        }
    }

    override fun onMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.item_list -> {
            createSelectListDialog().show()
            true
        }
        R.id.item_info -> {
            InfoBottomSheet.show(supportFragmentManager)
            true
        }
        else -> false
    }

    private fun createSelectListDialog(): AlertDialog {
        val builder = MaterialAlertDialogBuilder(this)

        val items = resources.getStringArray(R.array.statuses)
        with(builder) {
            setTitle(R.string.select_list)

            setSingleChoiceItems(items, currentList) { dialog, index ->
                viewModel.setRate(
                    rateId,
                    PatchUserRate.newInstance(
                        status = when (index) {
                            0 -> ListType.PLANNED
                            1 -> ListType.WATCHING
                            2 -> ListType.REWATCHING
                            3 -> ListType.COMPLETED
                            4 -> ListType.ON_HOLD
                            5 -> ListType.DROPPED
                            else -> null
                        }
                    )
                )

                currentList = index
                dialog.dismiss()
            }
            setNegativeButton(R.string.cancel, null)
        }

        return builder.create()
    }

    override fun onInflateView() = ActivityAnimeBinding.inflate(layoutInflater)

    companion object {
        private const val TAG = "[AnimeActivity]"

        private const val EXTRA_ID = "id"

        fun newIntent(context: Context, id: Long) =
            Intent(context, AnimeActivity::class.java)
            .putExtra(EXTRA_ID, id)
    }
}