package com.ilfey.shikimori.ui.anime.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentScreenshotsBinding
import com.ilfey.shikimori.ui.anime.AnimeViewModel
import com.ilfey.shikimori.utils.intArgument
import com.ilfey.shikimori.utils.stringArrayArgument
import com.ilfey.shikimori.utils.withArgs
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ScreenshotsFragment : BaseFragment<FragmentScreenshotsBinding>() {

    private val viewModel by activityViewModel<AnimeViewModel>()

    private val position by intArgument(ARG_POSITION)
    private val screenshots by stringArrayArgument(ARG_SCREENSHOTS)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(position!! + 1, screenshots?.size ?: 1)

        with(binding.pager) {
            adapter = ViewPagerAdapter(screenshots!!)
            setCurrentItem(position!!, false)
            offscreenPageLimit = 5
            registerOnPageChangeCallback(pageChangeCallback)
        }
    }

    private fun setToolbarTitle(pos: Int, max: Int) {
        activity?.findViewById<MaterialToolbar>(R.id.toolbar)?.title = getString(R.string.count_format, pos, max)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) = setToolbarTitle(position + 1, screenshots?.size ?: 1)

    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentScreenshotsBinding.inflate(inflater, container, false)

    override fun onDetach() {
        super.onDetach()
        activity?.findViewById<MaterialToolbar>(R.id.toolbar)?.title = viewModel.anime.value!!.russian
    }

    companion object {
        private const val ARG_SCREENSHOTS = "screenshots"
        private const val ARG_POSITION = "position"

        fun newInstance(
            screenshots: Array<String>,
            position: Int = 0,
        ) = ScreenshotsFragment().withArgs(2) {
            putInt(ARG_POSITION, position)
            putStringArray(ARG_SCREENSHOTS, screenshots)
        }
    }
}