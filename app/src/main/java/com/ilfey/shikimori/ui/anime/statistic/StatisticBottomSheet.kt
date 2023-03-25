package com.ilfey.shikimori.ui.anime.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseBottomSheetDialogFragment
import com.ilfey.shikimori.databinding.SheetAnimeStatisticBinding
import com.ilfey.shikimori.di.network.enums.ListTypes.*
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.parcelables.ParcelableScores
import com.ilfey.shikimori.di.network.models.parcelables.ParcelableStatuses
import com.ilfey.shikimori.utils.getParcelableCompat
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.withArgs

class StatisticBottomSheet : BaseBottomSheetDialogFragment<SheetAnimeStatisticBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStatusesChart()
        initScoresChart()

        val scores = arguments?.getParcelableCompat<ParcelableScores>(ARG_SCORES)?.scores
        val statuses = arguments?.getParcelableCompat<ParcelableStatuses>(ARG_STATUSES)?.statuses

        if (scores != null) {
            setDataToScoresChart(scores)
        } else {
            binding.chartScores.gone()
        }

        if (statuses != null) {
            setDataToStatusesChart(statuses)
        } else {
            binding.chartStatuses.gone()
        }
    }

    private fun initStatusesChart() {
        with(binding.chartStatuses) {
            description.isEnabled = false

            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisLeft.isInverted = true
            axisRight.isEnabled = false
            axisRight.isInverted = true
            /*axisLeft.setDrawLabels(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawLabels(false)
            axisRight.setDrawAxisLine(false)
            axisRight.setDrawGridLines(false)*/

            setNoDataText("")
//            setFitBars(true)
        }

        with(binding.chartStatuses.legend) {
            textSize = 14f
            orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            verticalAlignment =
                com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP
            horizontalAlignment =
                com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT
            xOffset = 22f
//            isEnabled = false
        }

        /*with(binding.listsChart.xAxis) {
            valueFormatter = IndexAxisValueFormatter(resources.getStringArray(R.array.statuses))
            val statuses = resources.getStringArray(R.array.statuses)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return // if (value <= statuses.size && (value * 10) % 10 == 0f) {
                    return statuses[value.toInt()]
                    } else {
                        ""
                    }
                }
            }
            position = XAxis.XAxisPosition.BOTTOM

            granularity = 1f

            setDrawAxisLine(false)
            setDrawGridLines(false)
        }*/
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

    private fun setDataToStatusesChart(statuses: List<Anime.RatesStatusesStats>) {
        val colors = mutableListOf<Int>()
        val labels = mutableListOf<String>()
        val ctx = requireContext()
        val entries = listOf(
            BarEntry(0f, statuses.map {
                colors.add(
                    when (it.name) {
                        PLANNED -> ctx.getColor(R.color.chart_planned)
                        WATCHING -> ctx.getColor(R.color.chart_watching)
                        REWATCHING -> ctx.getColor(R.color.chart_rewatching)
                        COMPLETED -> ctx.getColor(R.color.chart_completed)
                        ON_HOLD -> ctx.getColor(R.color.chart_on_hold)
                        DROPPED -> ctx.getColor(R.color.chart_dropped)
                    }
                )
                labels.add(
                    when (it.name) {
                        PLANNED -> getString(R.string.planned_with_count, it.value)
                        WATCHING -> getString(R.string.watching_with_count, it.value)
                        REWATCHING -> getString(
                            R.string.rewatching_with_count,
                            it.value
                        )
                        COMPLETED -> getString(R.string.completed_with_count, it.value)
                        ON_HOLD -> getString(R.string.on_hold_with_count, it.value)
                        DROPPED -> getString(R.string.dropped_with_count, it.value)
                    }
                )
                it.value.toFloat()
            }.toFloatArray())
        )

        val dataSet = BarDataSet(entries, "")
        dataSet.colors = colors
        dataSet.stackLabels = labels.toTypedArray()

        dataSet.setDrawIcons(false)
        dataSet.setDrawValues(false)

//        binding.listsChart.data = BarData(arrayListOf<IBarDataSet>(dataSet))
        binding.chartStatuses.data = BarData(dataSet)
        binding.chartStatuses.data.barWidth = 0.3f
        binding.chartStatuses.invalidate()
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
        container: ViewGroup?
    ) = SheetAnimeStatisticBinding.inflate(inflater, container, false)

    companion object {
        private const val ARG_STATUSES = "statuses"
        private const val ARG_SCORES = "scores"

        private const val TAG = "StatisticBottomSheet"
        fun show(
            fm: FragmentManager,
            scores: List<Anime.RatesScoresStats>,
            statuses: List<Anime.RatesStatusesStats>,
        ) {
            StatisticBottomSheet().withArgs(2){
                putParcelable(ARG_STATUSES, ParcelableStatuses(statuses))
                putParcelable(ARG_SCORES, ParcelableScores(scores))
            }.show(fm, TAG)
        }
    }
}