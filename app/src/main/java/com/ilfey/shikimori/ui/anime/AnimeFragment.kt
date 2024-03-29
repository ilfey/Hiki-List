package com.ilfey.shikimori.ui.anime

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentAnimeBinding
import com.ilfey.shikimori.di.network.enums.ListType.*
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.Role
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.ui.anime.utils.references
import com.ilfey.shikimori.utils.*
import com.ilfey.shikimori.utils.widgets.HorizontalSpaceItemDecorator
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import com.google.android.material.R as mR
import com.ilfey.shikimori.di.network.entities.Anime as eAnime


class AnimeFragment : BaseFragment<FragmentAnimeBinding>(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by activityViewModel<AnimeViewModel>()

    val id by longArgument(ARG_ID)

    private var rateId: Long? = null
    private var currentList = -1
    private var descriptionIsCollapsed = false
    private var collapsedMaxLines = 4
    private var posterUrl = ""
    private var scoresStats = listOf<eAnime.RatesScoresStats>()
    private var statusesStats = listOf<eAnime.RatesStatusesStats>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.expandDescriptionBtn.setOnClickListener(this)
        binding.description.setOnClickListener(this)
        with(binding.refresh) {
            setProgressBackgroundColorSchemeColor(context.getThemeColor(mR.attr.colorPrimary))
            setColorSchemeColors(context.getThemeColor(mR.attr.colorOnPrimary))
            setOnRefreshListener(this@AnimeFragment)
        }

        // Create references
        references()
    }

    override fun bindViewModel() {
        viewModel.anime.observe(viewLifecycleOwner, this::onAnimeUpdate)
        viewModel.userRate.observe(viewLifecycleOwner, this::onUserRateUpdate)
        viewModel.roles.observe(viewLifecycleOwner, this::onRolesUpdate)
    }

    private fun onAnimeUpdate(anime: Anime) {
        posterUrl = anime.image

        if (anime.scoresStats != null) {
            scoresStats = anime.scoresStats
        }

        if (anime.statusesStats != null) {
            statusesStats = anime.statusesStats
        }

        Glide
            .with(this)
            .load(posterUrl)
            .into(binding.poster)

        with(binding) {
            if (settings.isEnLocale) {
                primaryTitle.text = anime.titleEn
                secondaryTitle.text = anime.titleRu
            } else {
                primaryTitle.text = anime.titleRu
                secondaryTitle.text = anime.titleEn
            }

            if (anime.kind != null) {
                type.text = anime.kind
                type.visible()
            } else {
                type.gone()
            }

            if (anime.episodes != null) {
                episodes.text = anime.episodes
                episodes.visible()
            } else {
                episodes.gone()
            }

            status.text = anime.status

            if (anime.score != null) {
                score.text = anime.score
                score.visible()
            } else {
                score.gone()
            }

            if (anime.description != null) {
                // TODO: compile bb-code
                description.text = anime.description
            } else {
                description.text = getString(R.string.no_description)
            }

            if (description.lineCount < collapsedMaxLines) {
                expandDescriptionBtn.gone()
            } else {
                expandDescriptionBtn.visible()
            }

            if (anime.screenshots != null) {
                if (screenshots.adapter == null) {
                    screenshots.adapter = ScreenshotsAdapter(this@AnimeFragment)
                    screenshots.addItemDecoration(
                        HorizontalSpaceItemDecorator(
                            videos.context.dp(12),
                        ),
                    )
                }

                (screenshots.adapter as ScreenshotsAdapter).setList(anime.screenshots)
                screenshotsGroup.visible()
            } else {
                screenshotsGroup.gone()
            }

            if (anime.videos != null) {
                if (videos.adapter == null) {
                    videos.adapter = VideosAdapter(anime.videos)
                    videos.addItemDecoration(HorizontalSpaceItemDecorator(videos.context.dp(12)))
                }

                (videos.adapter as VideosAdapter).setList(anime.videos)
            } else {
                videosGroup.gone()
            }

            genresContainer.removeAllViews()

            if (anime.studios != null) {
                anime.studios.map {
                    genresContainer.run { addView(createChip(it.name, true)) }
                }
                genresContainer.visible()
            } else {
                genresContainer.gone()
            }

            if (anime.genres != null) {
                anime.genres.map {
                    genresContainer.run { addView(createChip(it.russian)) }
                }
                genresContainer.visible()
            } else {
                genresContainer.gone()
            }
        }
    }

    private fun onUserRateUpdate(rate: UserRate) {
        rateId = rate.id

        currentList = when (rate.list) {
            PLANNED -> 0
            WATCHING -> 1
            REWATCHING -> 2
            COMPLETED -> 3
            ON_HOLD -> 4
            DROPPED -> 5
        }
    }

    private fun onRolesUpdate(roles: List<Role>) {
        val listCharacters = roles.filter { it.character != null }
        val mainCharacters = listCharacters.filter { "Main" in it.roles }

        with(binding) {
            if (mainCharacters.isNotEmpty()) {
                if (characters.adapter == null) {
                    characters.adapter = CharacterAdapter(mainCharacters)
                    characters.addItemDecoration(
                        HorizontalSpaceItemDecorator(
                            characters.context.dp(12),
                        ),
                    )
                } else {
                    (characters.adapter as CharacterAdapter).setList(mainCharacters)
                }
            } else {
                charactersGroup.gone()
            }
        }
    }

//    private fun openScreenshot(screenshots: Array<String>, pos: Int = 0) {
//        parentFragmentManager.commit {
//            add(R.id.container, ScreenshotsFragment.newInstance(screenshots, pos))
//            addToBackStack(null)
//        }
//    }

    private fun ChipGroup.createChip(text: String, isPrimary: Boolean = false): Chip {
        val chip = Chip(context)

        if (isPrimary) {
            chip.chipBackgroundColor = ColorStateList.valueOf(context.getThemeColor(mR.attr.colorTertiary))
            chip.setTextColor(context.getThemeColor(mR.attr.colorOnTertiary))
        }

        chip.text = text
        chip.isEnabled = false

        return chip
    }

    private fun expandDescription() {
        val tv = binding.description
        val btn = binding.expandDescriptionBtn

        btn.text = getString(
            when (descriptionIsCollapsed) {
                true -> R.string.show_description
                false -> R.string.hide_description
            }
        )

        val anim = ObjectAnimator.ofInt(
            tv,
            "maxLines",
            if (tv.maxLines == collapsedMaxLines) tv.lineCount else collapsedMaxLines
        )

        anim.duration = ((tv.lineCount - collapsedMaxLines) * 10).toLong()
        anim.start()

        descriptionIsCollapsed = !descriptionIsCollapsed
    }

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.poster -> openScreenshot(arrayOf(posterUrl))

            R.id.expand_description_btn -> expandDescription()
        }
    }

    override fun onRefresh() {
        binding.refresh.isRefreshing = true
        viewModel.onRefresh()
        binding.refresh.isRefreshing = true
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAnimeBinding.inflate(inflater, container, false)

    companion object {
        private const val TAG = "[AnimeFragment]"

        private const val ARG_ID = "id"
        fun newInstance(id: Long) = AnimeFragment().withArgs(1) {
            putLong(ARG_ID, id)
        }
    }
}