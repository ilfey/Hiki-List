package com.ilfey.shikimori.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseBottomSheetDialogFragment
import com.ilfey.shikimori.databinding.SheetAnimeStatisticBinding
import com.ilfey.shikimori.di.network.enums.ListTypes.*
import com.ilfey.shikimori.di.network.models.Anime

class AnimeStatisticBottomSheet : BaseBottomSheetDialogFragment<SheetAnimeStatisticBinding>() {

    private val args by navArgs<AnimeStatisticBottomSheetArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListsChart()
        initRatesChart()

        setDataToListsChart(args.lists)
        setDataToRatesChart(args.rates)
    }

    private fun initListsChart() {
        with(binding.chartLists) {
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

        with(binding.chartLists.legend) {
            textSize = 14f
            orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT
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

    private fun initRatesChart() {
        with(binding.chartRates) {
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

    private fun setDataToListsChart(statuses: Array<Anime.RatesStatusesStats>) {
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
        binding.chartLists.data = BarData(dataSet)
        binding.chartLists.data.barWidth = 0.3f
        binding.chartLists.invalidate()
    }

    private fun setDataToRatesChart(scores: Array<Anime.RatesScoresStats>) {
        val entries = scores.map { e ->
            BarEntry(e.name.toFloat(), e.value.toFloat())
        }
        val dataSet = BarDataSet(entries, "")

        binding.chartRates.data = BarData(dataSet)
        binding.chartRates.data.barWidth = 1f
        binding.chartRates.invalidate()
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = SheetAnimeStatisticBinding.inflate(inflater, container, false)
}