package com.ilfey.shikimori.ui.home

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentHomeBinding
import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.di.network.models.filterByStatus
import com.ilfey.shikimori.utils.getThemeColor
import com.ilfey.shikimori.utils.gone
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by inject<HomeViewModel>()

    private val customTabsIntent = CustomTabsIntent.Builder().build()

    private val isRefreshEnabled = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.chart) {
            isSelected = false
            isRotationEnabled = false
//            isDrawHoleEnabled = false
            description.isEnabled = false
            isHighlightPerTapEnabled = false
            setDrawEntryLabels(false)
            setDrawCenterText(false)
            transparentCircleRadius = 0f

            setNoDataText("")

            setExtraOffsets(55f, 0f, 0f, 0f)
        }

        with(binding.chart.legend) {
            textSize = 14f
            orientation = Legend.LegendOrientation.VERTICAL
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            xOffset = 22f
//            isEnabled = false
        }

        with(binding.refresh) {
            setProgressBackgroundColorSchemeColor(context.getThemeColor(com.google.android.material.R.attr.colorPrimary))
            setColorSchemeColors(context.getThemeColor(com.google.android.material.R.attr.colorOnPrimary))
            setOnRefreshListener(this@HomeFragment)
            isEnabled = this@HomeFragment.isRefreshEnabled
        }

        binding.historyBtn.setOnClickListener(this)
        binding.favoritesBtn.setOnClickListener(this)
    }

    override fun bindViewModel() {
        viewModel.user.observe(viewLifecycleOwner) {

            Glide
                .with(this)
                .load(it.image.x160)
                .circleCrop()
                .into(binding.avatar)

            binding.username.text = it.nickname

            if (it.full_years != 0) {
                binding.age.text = getAge(it.full_years)
            } else {
                binding.age.gone()
            }

            if (it.website?.isNotEmpty() == true) {
                val text = getString(R.string.website)
                val spannableString = SpannableStringBuilder(text)

                spannableString.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            customTabsIntent.launchUrl(requireContext(), Uri.parse(it.website))
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            // Стиль ссылки
                            ds.isUnderlineText = true
                            ds.color = Color.BLUE
                        }
                    }, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.website.text = spannableString
                binding.website.movementMethod = LinkMovementMethod.getInstance()
            } else {
                binding.website.gone()
            }
        }
        viewModel.user_rates.observe(viewLifecycleOwner) {
            setData(it)
        }
    }
    private fun setData(rates: List<UserRate>) {
        val statuses = arrayListOf(
            rates.filterByStatus(ListTypes.PLANNED).count(),
            rates.filterByStatus(ListTypes.WATCHING).count(),
            rates.filterByStatus(ListTypes.REWATCHING).count(),
            rates.filterByStatus(ListTypes.COMPLETED).count(),
            rates.filterByStatus(ListTypes.ON_HOLD).count(),
            rates.filterByStatus(ListTypes.DROPPED).count(),
        )

        val statuses_titles = resources.getStringArray(R.array.statuses_with_count)

        val entries = statuses.mapIndexed { i, e ->
            PieEntry(e.toFloat(), String.format(statuses_titles[i], e))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawValues(false)
//        dataSet.selectionShift = 10f
        dataSet.colors = resources.getIntArray(R.array.chart_colors).toList()
        val data = PieData(dataSet)

        binding.chart.data = data
        binding.chart.invalidate()
    }

    private fun getAge(age: Int): String {
        if (age in 12..13) {
            getString(R.string.age_3, age)
        } else
        if (age % 10 == 1) {
            return getString(R.string.age_1, age)
        } else if (age % 10 in 2..4) {
            return getString(R.string.age_2, age)
        }

        return getString(R.string.age_3, age)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.history_btn -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
            }
            R.id.favorites_btn -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoritesFragment())
            }
            else -> {}
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
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater)
}