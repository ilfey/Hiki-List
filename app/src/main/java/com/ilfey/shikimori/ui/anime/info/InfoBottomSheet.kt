package com.ilfey.shikimori.ui.anime.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.chip.Chip
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseBottomSheetDialogFragment
import com.ilfey.shikimori.databinding.SheetAnimeInfoBinding
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.ui.anime.AnimeViewModel
import com.ilfey.shikimori.utils.gone
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class InfoBottomSheet : BaseBottomSheetDialogFragment<SheetAnimeInfoBinding>() {

    private val viewModel by activityViewModel<AnimeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScoresChart()
    }

    override fun bindViewModel() {
        viewModel.anime.observe(viewLifecycleOwner, this::onAnimeUpdate)
    }

    private fun onAnimeUpdate(anime: Anime) {
        /* TYPE */

        binding.type.text = when (anime.kind) {
            Kind.TV -> getString(R.string.type_tv)
            Kind.MOVIE -> getString(R.string.type_movie)
            Kind.OVA -> getString(R.string.type_ova)
            Kind.ONA -> getString(R.string.type_ona)
            Kind.SPECIAL -> getString(R.string.type_special)
            Kind.MUSIC -> getString(R.string.type_music)
            Kind.TV_13 -> getString(R.string.type_tv)
            Kind.TV_24 -> getString(R.string.type_tv)
            Kind.TV_48 -> getString(R.string.type_tv)
            else -> {
                binding.type.gone()
                ""
            }
        }

        /* STUDIOS */

        if (anime.studios.isNotEmpty()) {
            binding.studio.text =
                getString(R.string.studios, anime.studios.joinToString { studio -> studio.name })
        } else {
            binding.studio.gone()
        }

        /* JAPANESE */

        if (anime.japanese.isNotEmpty()) {
            binding.inJapanese.text =
                getString(R.string.in_japanese, anime.japanese.joinToString { s -> s.toString() })
        } else {
            binding.inJapanese.gone()
        }

        /* ENGLISH */

        if (anime.english.isNotEmpty()) {
            binding.inEnglish.text =
                getString(R.string.in_english, anime.english.joinToString { s -> s.toString() })
        } else {
            binding.inEnglish.gone()
        }

        /* ALTERNATIVE TITLES */

        if (anime.synonyms.isNotEmpty()) {
            binding.altTitles.text =
                getString(R.string.alt_titles, anime.synonyms.joinToString { s -> s.toString() })
        } else {
            binding.altTitles.gone()
        }

        /* USER LISTS */

        if (anime.rates_statuses_stats.isNotEmpty()){
            anime.rates_statuses_stats.map {
                binding.chipGroupStatuses.addView(createChip( when (it.name) {
                    ListType.PLANNED -> getString(R.string.planned_with_count, it.value)
                    ListType.WATCHING -> getString(R.string.watching_with_count, it.value)
                    ListType.REWATCHING -> getString(
                        R.string.rewatching_with_count,
                        it.value
                    )
                    ListType.COMPLETED -> getString(R.string.completed_with_count, it.value)
                    ListType.ON_HOLD -> getString(R.string.on_hold_with_count, it.value)
                    ListType.DROPPED -> getString(R.string.dropped_with_count, it.value)
                }))
            }
        } else {
            binding.textViewLists.gone()
            binding.scrollViewStatuses.gone()
        }

        /* SCORES */

        if (anime.rates_scores_stats.isNotEmpty()) {
            setDataToScoresChart(anime.rates_scores_stats)
        } else {
            binding.textViewRates.gone()
            binding.chartScores.gone()
        }

        /* FUNDUBBERS */

        if (anime.fandubbers.isNotEmpty()) {
            binding.fandubbers.adapter = StringAdapter(anime.fandubbers)
        } else {
            binding.textViewFandubbers.gone()
            binding.fandubbers.gone()
        }
        if (anime.fansubbers.isNotEmpty()) {
            binding.fansubbers.adapter = StringAdapter(anime.fansubbers)
        } else {
            binding.textViewFansubbers.gone()
            binding.fansubbers.gone()
        }
    }


    private fun createChip(text: String): Chip {
        val chip = Chip(requireContext())

        chip.text = text
        chip.isEnabled = false

        return chip
    }

    private fun initScoresChart() {
        with(binding.chartScores) {
            description.isEnabled = false
            legend.isEnabled = false

            xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.setDrawAxisLine(false)
            xAxis.setDrawGridLines(false)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false

            setNoDataText("")
        }
    }
    private fun setDataToScoresChart(scores: List<Anime.RatesScoresStats>) {
        val entries = scores.map { e ->
            BarEntry(e.name.toFloat(), e.value.toFloat())
        }
        val dataSet = BarDataSet(entries, "")

        binding.chartScores.data = BarData(dataSet)
        binding.chartScores.data.barWidth = 1f
        binding.chartScores.invalidate()
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = SheetAnimeInfoBinding.inflate(inflater, container, false)

    companion object {
        private const val TAG = "[InfoBottomSheet]"

        fun show(fm: FragmentManager) = InfoBottomSheet().show(fm, TAG)
    }
}