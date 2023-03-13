package com.ilfey.shikimori.ui.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentScreenshotsBinding
import com.ilfey.shikimori.utils.addBackButton

class ScreenshotsFragment : BaseFragment<FragmentScreenshotsBinding>() {

    private val args by navArgs<ScreenshotsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.pager) {
            adapter = ViewPagerAdapter(args.screenshots)
            currentItem = args.pos
            offscreenPageLimit = 5
            registerOnPageChangeCallback(pageChangeCallback)
        }

        binding.toolbar.run {
            addBackButton { activity?.onBackPressedDispatcher?.onBackPressed() }
            title = String.format(
                getString(R.string.count_format),
                args.pos + 1,
                args.screenshots.size,
            )
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.toolbar.title = String.format(
                getString(R.string.count_format),
                position + 1,
                args.screenshots.size,
            )
        }
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScreenshotsBinding.inflate(inflater, container, false)
}