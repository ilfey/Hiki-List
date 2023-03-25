package com.ilfey.shikimori.ui.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentScreenshotsBinding
import com.ilfey.shikimori.utils.addBackButton
import com.ilfey.shikimori.utils.intArgument
import com.ilfey.shikimori.utils.stringArrayArgument
import com.ilfey.shikimori.utils.withArgs

class ScreenshotsFragment : BaseFragment<FragmentScreenshotsBinding>() {

    private val position by intArgument(ARG_POSITION)
    private val screenshots by stringArrayArgument(ARG_SCREENSHOTS)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            addBackButton { activity?.onBackPressedDispatcher?.onBackPressed() }
            title = String.format(
                getString(R.string.count_format),
                position!! + 1,
                screenshots!!.size,
            )
        }
        with(binding.pager) {
            adapter = ViewPagerAdapter(screenshots!!)
            setCurrentItem(position!!, false)
            offscreenPageLimit = 5
            registerOnPageChangeCallback(pageChangeCallback)
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.toolbar.title = String.format(
                getString(R.string.count_format),
                position + 1,
                screenshots!!.size,
            )
        }
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentScreenshotsBinding.inflate(inflater, container, false)

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