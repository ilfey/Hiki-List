package com.ilfey.shikimori.ui.anime.screenshots

import android.os.Bundle
import android.util.Log
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
import com.ilfey.shikimori.utils.withArgs
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ScreenshotsFragment : BaseFragment<FragmentScreenshotsBinding>() {

    private val viewModel by activityViewModel<AnimeViewModel>()

    private val position by intArgument(ARG_POSITION)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: start $position screenshot")
        
        with(binding.pager) {
            setCurrentItem(position!!, false)
            offscreenPageLimit = 5
            registerOnPageChangeCallback(pageChangeCallback)
        }
    }

    override fun bindViewModel() {
        viewModel.anime.observe(viewLifecycleOwner) {
            if (it.screenshots != null) {
                setToolbarTitle(position!! + 1, it.screenshots.size)
                binding.pager.adapter = ViewPagerAdapter(it.screenshots)
                binding.pager.setCurrentItem(position!!, false)
            }
//            TODO: handle no screenshots
        }
    }

    private fun setToolbarTitle(pos: Int, max: Int) {
        activity?.findViewById<MaterialToolbar>(R.id.toolbar)?.title = getString(R.string.count_format, pos, max)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) = setToolbarTitle(position + 1, viewModel.anime.value?.screenshots?.size ?: 1)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentScreenshotsBinding.inflate(inflater, container, false)

    override fun onDetach() {
        super.onDetach()
        activity?.findViewById<MaterialToolbar>(R.id.toolbar)?.title = if (settings.isEnLocale) viewModel.anime.value!!.titleEn else viewModel.anime.value!!.titleRu
    }

    companion object {
        private const val TAG = "[ScreenshotsFragment]"
        private const val ARG_POSITION = "position"

        fun newInstance(
            position: Int = 0,
        ) = ScreenshotsFragment().withArgs(1) {
            putInt(ARG_POSITION, position)
        }
    }
}