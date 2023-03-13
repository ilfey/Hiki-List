package com.ilfey.shikimori.ui.anime

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentAnimeBinding
import com.ilfey.shikimori.utils.addBackButton
import com.ilfey.shikimori.utils.dp
import com.ilfey.shikimori.utils.getThemeColor
import com.ilfey.shikimori.utils.toast
import com.ilfey.shikimori.utils.widgets.HorizontalSpaceItemDecorator
import org.koin.android.ext.android.inject
import com.google.android.material.R as MaterialR


class AnimeFragment : BaseFragment<FragmentAnimeBinding>(), View.OnClickListener,
    OnRatingBarChangeListener, Toolbar.OnMenuItemClickListener {

    private val viewModel by inject<AnimeViewModel>()
    private val args by navArgs<AnimeFragmentArgs>()

    private var isCollapsed = false
    private var collapsedMaxLines = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAnime(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            addBackButton { activity?.onBackPressedDispatcher?.onBackPressed() }
            inflateMenu(R.menu.anime_toolbar_menu)
            setOnMenuItemClickListener(this@AnimeFragment)
        }

        binding.showDescriptionBtn.setOnClickListener(this)
        binding.description.setOnClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.item_list -> {
            context?.toast()
            true
        }
        R.id.item_statistic -> {
            findNavController().navigate(
                AnimeFragmentDirections.actionAnimeFragmentToAnimeStatisticBottomSheet(
                    viewModel.anime.value?.rates_scores_stats?.toTypedArray() ?: arrayOf(),
                    viewModel.anime.value?.rates_statuses_stats?.toTypedArray() ?: arrayOf(),
                )
            )
            true
        }
        else -> false
    }

    override fun bindViewModel() {
        viewModel.anime.observe(viewLifecycleOwner) {
            Glide
                .with(this)
                .load(BuildConfig.APP_URL + it.image.original)
                .into(binding.poster)

            with(binding) {
                name.text = it.name
                title.text = it.russian
                it.score.toFloat().let { score ->
                    if (score != 0f) {
                        scoreRatingBar.rating = score / 2
                    } else {
                        scoreRatingBar.visibility = View.GONE
                    }
                }

                if (it.description == null) {
                    showDescriptionBtn.visibility = View.GONE
                    description.text = getString(R.string.no_description)
                } else {
                    description.text = it.description
                }
//                userRating.text = it.user_rate?.score.toString()
//                userRatingBar.rating = it.user_rate?.score?.toFloat()?.div(2) ?: 0f
//                mpaaRating.text = when (it.rating) {
//                    G -> getString(R.string.rating_g)
//                    NONE -> getString(R.string.rating_none)
//                    PG -> getString(R.string.rating_pg)
//                    PG_13 -> getString(R.string.rating_pg_13)
//                    R -> getString(R.string.rating_r)
//                    R_PLUS -> getString(R.string.rating_r_plus)
//                    RX -> getString(R.string.rating_rx)
//                    null -> getString(R.string.rating_none)
//                }

                if (it.screenshots.isEmpty()) {
                    screenshotsGroup.visibility = View.GONE
                } else {
                    if (screenshots.adapter == null) {
                        screenshots.adapter = ScreenshotsAdapter(
                            this@AnimeFragment,
                            it.screenshots,
                        )
                        screenshots.addItemDecoration(
                            HorizontalSpaceItemDecorator(
                                videos.context.dp(12),
                            ),
                        )
                    } else {
                        (screenshots.adapter as ScreenshotsAdapter).setList(it.screenshots)
                    }
                }

                if (it.videos.isEmpty()) {
                    videosGroup.visibility = View.GONE
                } else {
                    if (videos.adapter == null) {
                        videos.adapter = VideosAdapter(it.videos)
                        videos.addItemDecoration(HorizontalSpaceItemDecorator(videos.context.dp(12)))
                    } else {
                        (videos.adapter as VideosAdapter).setList(it.videos)
                    }
                }
            }

            if (it.studios.isNotEmpty()) {
                it.studios.map { studio ->
                    binding.genresContainer.addView(createChip(studio.name))
                }
            }

            if (it.genres.isNotEmpty()) {
                it.genres.map { genre ->
                    binding.genresContainer.addView(createChip(genre.russian))
                }
            } else {
                binding.genresContainer.visibility = View.GONE
            }
        }
    }

    private fun openScreenshot(screenshots: Array<String>, pos: Int = 0) {
        findNavController().navigate(
            AnimeFragmentDirections.actionAnimeFragmentToScreenshotsFragment(
                screenshots,
                pos,
            )
        )
    }

    private fun createChip(text: String): Chip {
        val chip = Chip(requireContext())

        chip.text = text
//        chip.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
//        chip.textSize = 12f
        chip.isEnabled = false
        chip.chipBackgroundColor = ColorStateList.valueOf(
            requireContext().getThemeColor(MaterialR.attr.colorOnBackground)
        )
        chip.setTextAppearance(R.style.TextAppearance_Shikimori_Body1)
        return chip
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.showDescriptionBtn.id -> {
                binding.showDescriptionBtn.text =
                    getString(if (isCollapsed) R.string.show_description else R.string.hide_description)
                cycleTextViewExpansion(binding.description)
                isCollapsed = !isCollapsed
            }
            else -> {}
        }
    }

    override fun onRatingChanged(v: RatingBar, rating: Float, fromUser: Boolean) {
//        binding.userRating.text = (rating * 2).toInt().toString()
//        TODO post rating
    }

    private fun cycleTextViewExpansion(tv: TextView) {
        val anim = ObjectAnimator.ofInt(
            tv,
            "maxLines",
            if (tv.maxLines == collapsedMaxLines) tv.lineCount else collapsedMaxLines
        )

        anim.duration = ((tv.lineCount - collapsedMaxLines) * 10).toLong()
        anim.start()
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAnimeBinding.inflate(inflater, container, false)
}