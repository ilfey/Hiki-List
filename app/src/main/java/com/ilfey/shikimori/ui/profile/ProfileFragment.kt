package com.ilfey.shikimori.ui.profile

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentProfileBinding
import com.ilfey.shikimori.di.network.enums.ListType.*
import com.ilfey.shikimori.di.network.models.User
import com.ilfey.shikimori.ui.favorites.FavoritesActivity
import com.ilfey.shikimori.ui.history.HistoryActivity
import com.ilfey.shikimori.ui.profile.friends.FriendsBottomSheet
import com.ilfey.shikimori.ui.settings.SettingsActivity
import com.ilfey.shikimori.utils.*
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    private val username by stringArgument(ARG_USERNAME)
    private val viewModel by activityViewModel<ProfileViewModel>()

    private val customTabsIntent = CustomTabsIntent.Builder().build()
    private val isRefreshEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (username != null) {
            viewModel.getUser(username!!)
        } else {
            Log.e(TAG, "onCreate: username cannot be null")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            inflateMenu(R.menu.profile_toolbar_menu)
            setOnMenuItemClickListener(this@ProfileFragment)
        }

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
            setOnRefreshListener(this@ProfileFragment)
            isEnabled = this@ProfileFragment.isRefreshEnabled
        }

        binding.favoritesBtn.setOnClickListener(this)
        binding.clubsBtn.setOnClickListener(this)
        binding.friendsBtn.setOnClickListener(this)
        binding.historyBtn.setOnClickListener(this)
    }

    override fun bindViewModel() {
        viewModel.user.observe(viewLifecycleOwner, this::onUserUpdate)
    }

    private fun onUserUpdate(user: User) {
        Glide
            .with(this)
            .load(user.avatar)
            .circleCrop()
            .into(binding.avatar)

        binding.username.text = user.username

        if (user.age != null) {
            binding.age.text = user.age
        } else {
            binding.age.gone()
        }

        if (user.website != null) {
            val text = getString(R.string.website)
            val spannableString = SpannableStringBuilder(text)

            spannableString.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        customTabsIntent.launchUrl(requireContext(), Uri.parse(user.website))
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

        setData(user.stats.statuses.anime)
    }

    private fun setData(rates: List<User.Stats.Statuses.Status>) {
        val statuses = rates.map {
            it.size
        }

//        val statuses_titles = resources.getStringArray(R.array.statuses_with_count)
        val titles = rates.map {
            when (it.list) {
                PLANNED -> getString(R.string.planned_with_count, it.size)
                WATCHING -> getString(R.string.watching_with_count, it.size)
                REWATCHING -> getString(R.string.rewatching_with_count, it.size)
                COMPLETED -> getString(R.string.completed_with_count, it.size)
                ON_HOLD -> getString(R.string.on_hold_with_count, it.size)
                DROPPED -> getString(R.string.dropped_with_count, it.size)
            }
        }

        val entries = statuses.mapIndexed { i, e ->
            PieEntry(e.toFloat(), String.format(titles[i], e))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawValues(false)
//        dataSet.selectionShift = 10f
        dataSet.colors = resources.getIntArray(R.array.chart_colors).toList()
        val data = PieData(dataSet)

        binding.chart.data = data
        binding.chart.invalidate()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.favorites_btn -> {
                val intent = FavoritesActivity.newIntent(v.context)
                startActivity(intent)
            }
            R.id.clubs_btn -> v.context.toast()
            R.id.friends_btn -> FriendsBottomSheet.show(parentFragmentManager, username!!)
            R.id.history_btn -> {
                val intent = HistoryActivity.newIntent(v.context)
                startActivity(intent)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem) =
        when (item.itemId) {
            R.id.item_settings -> {
                val intent = SettingsActivity.newIntent(requireContext())
                startActivity(intent)
                true
            }
            else -> {
                false
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
    ) = FragmentProfileBinding.inflate(inflater)

    companion object {
        private const val TAG = "[ProfileFragment]"

        private const val ARG_USERNAME = "username"

        fun newInstance(
            username: String
        ) = ProfileFragment().withArgs(1) {
            putString(ARG_USERNAME, username)
        }
    }
}