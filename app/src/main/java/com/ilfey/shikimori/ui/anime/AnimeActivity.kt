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
import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.ui.anime.info.InfoBottomSheet
import com.ilfey.shikimori.ui.anime.statistic.StatisticBottomSheet
import com.ilfey.shikimori.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeActivity : BaseActivity<ActivityAnimeBinding>(), Toolbar.OnMenuItemClickListener {

    private val viewModel by viewModel<AnimeViewModel>()
    private var id: Long = 0
    private var rateId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.data
        if (url == null) {
            id = intent.getLongExtra(EXTRA_ID, 1) // TODO: Handle error
            Log.d(TAG, "onCreate: start with id: $id")
        } else {
            val reg = Regex("\\d+")
            val idString = reg.find(url.lastPathSegment.toString())?.value
            id = idString?.toLong() ?: 1 // TODO: Handle error
            Log.d(TAG, "onCreate: start with url: $url")
        }

        binding.toolbar.run {
            addBackButton { onBackPressedDispatcher.onBackPressed() }
            inflateMenu(R.menu.anime_toolbar_menu)
            setOnMenuItemClickListener(this@AnimeActivity) // TODO: Delegate this
        }

        viewModel.getAnime(id)
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
        binding.toolbar.title = anime.russian
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, AnimeFragment.newInstance(anime.id))
        }
    }

    private fun onRateUpdate(rate: UserRate) {
        rateId = rate.id
    }

    override fun onMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.item_list -> {
            createSelectListDialog().show()
            true
        }
        R.id.item_statistic -> {
            StatisticBottomSheet.show(
                supportFragmentManager,
                viewModel.anime.value?.rates_scores_stats ?: listOf(),
                viewModel.anime.value?.rates_statuses_stats ?: listOf(),
            )
            true
        }
        R.id.item_info -> {
            InfoBottomSheet.show(
                supportFragmentManager,
                viewModel.anime.value?.kind,
                viewModel.anime.value?.studios?.map { studio -> studio.name }?.toTypedArray(),
                viewModel.anime.value?.japanese?.filterNotNull()?.toTypedArray(),
                viewModel.anime.value?.english?.filterNotNull()?.toTypedArray(),
                viewModel.anime.value?.synonyms?.filterNotNull()?.toTypedArray(),
                viewModel.anime.value?.fandubbers?.toTypedArray(),
                viewModel.anime.value?.fansubbers?.toTypedArray(),
            )
            true
        }
        else -> false
    }

    private var currentList = -1

    private fun createSelectListDialog(): AlertDialog { // TODO: Delegate this
        val builder = MaterialAlertDialogBuilder(this)

        val items = resources.getStringArray(R.array.statuses)
        with(builder) {
            setTitle(R.string.select_list)

            setSingleChoiceItems(items, currentList) { dialog, index ->
                viewModel.setRate(
                    rateId,
                    PatchUserRate.UserRate(
                        status = when (index) {
                            0 -> ListTypes.PLANNED
                            1 -> ListTypes.WATCHING
                            2 -> ListTypes.REWATCHING
                            3 -> ListTypes.COMPLETED
                            4 -> ListTypes.ON_HOLD
                            5 -> ListTypes.DROPPED
                            else -> null
                        }
                    )
                )

                currentList = index
                dialog.dismiss()
            }
            setNegativeButton(R.string.cancel, null)
        }

        val dialog = builder.create()

        return dialog
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