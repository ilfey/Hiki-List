package com.ilfey.shikimori.ui.anime

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentAnimeBinding
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.Kind.*
import com.ilfey.shikimori.di.network.enums.ListTypes.*
import com.ilfey.shikimori.di.network.enums.Rating.*
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.Role
import com.ilfey.shikimori.di.network.models.characters
import com.ilfey.shikimori.di.network.models.mainCharacters
import com.ilfey.shikimori.utils.addBackButton
import com.ilfey.shikimori.utils.dp
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.toast
import com.ilfey.shikimori.utils.widgets.HorizontalSpaceItemDecorator
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat


class AnimeFragment : BaseFragment<FragmentAnimeBinding>(), View.OnClickListener,
    OnRatingBarChangeListener, Toolbar.OnMenuItemClickListener {

    private val viewModel by inject<AnimeViewModel>()
    private val args by navArgs<AnimeFragmentArgs>()

    private var currentList = -1
    private var descriptionIsCollapsed = false
    private var collapsedMaxLines = 4
    private var posterUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRoles(args.id)
        viewModel.getAnime(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            addBackButton { activity?.onBackPressedDispatcher?.onBackPressed() }
            inflateMenu(R.menu.anime_toolbar_menu)
            setOnMenuItemClickListener(this@AnimeFragment)
        }

        binding.expandDescriptionBtn.setOnClickListener(this)
        binding.description.setOnClickListener(this)
    }

    private fun createSelectListDialog(): AlertDialog {
        val builder = MaterialAlertDialogBuilder(requireContext())

        val items = resources.getStringArray(R.array.statuses)
        with(builder) {
            setTitle(R.string.select_list)

            setSingleChoiceItems(items, currentList) { dialog, index ->
                viewModel.setStatus(
                    args.id,
                    PatchUserRate.UserRate(
                        status = when (index) {
                            0 -> { // PLANNED
                                PLANNED
                            }
                            1 -> { // WATCHING
                                WATCHING
                            }
                            2 -> { // REWATCHING
                                REWATCHING
                            }
                            3 -> { // COMPLETED
                                COMPLETED
                            }
                            4 -> { // ON_HOLD
                                ON_HOLD
                            }
                            5 -> { // DROPPED
                                DROPPED
                            }
                            else -> {
                                PLANNED
                            }
                        }
                    )
                )

//                context.toast()
                currentList = index
                dialog.dismiss()
            }
            setNegativeButton(R.string.cancel, null)
        }

        val dialog = builder.create()

        return dialog
    }

    override fun onMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.item_list -> {
            createSelectListDialog().show()
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
        R.id.item_info -> {
//            context?.toast()
            findNavController().navigate(
                AnimeFragmentDirections.actionAnimeFragmentToAnimeInfoBottomSheet(
                    viewModel.anime.value!!
                )
            )
            true
        }
        else -> false
    }

    override fun bindViewModel() {
        viewModel.anime.observe(viewLifecycleOwner, this::onAnimeUpdate)
        viewModel.roles.observe(viewLifecycleOwner, this::onRolesUpdate)
    }

    private fun onAnimeUpdate(anime: Anime) {
        val dateFormat = SimpleDateFormat(
            "dd MMMM yyyy",
            requireContext().resources.configuration.locales.get(0),
        )
        posterUrl = BuildConfig.APP_URL + anime.image.original

        Glide
            .with(this)
            .load(posterUrl)
            .into(binding.poster)

        with(binding) {
            title.text = anime.russian
            name.text = anime.name
            type.text = when (anime.kind) {
                TV -> getString(R.string.type_tv)
                MOVIE -> getString(R.string.type_movie)
                OVA -> getString(R.string.type_ova)
                ONA -> getString(R.string.type_ona)
                SPECIAL -> getString(R.string.type_special)
                MUSIC -> getString(R.string.type_music)
                TV_13 -> getString(R.string.type_tv)
                TV_24 -> getString(R.string.type_tv)
                TV_48 -> getString(R.string.type_tv)
                else -> {
                    type.gone()
                    ""
                }
            }
            if (anime.episodes != 0) {
                episodes.text = if (anime.episodes == anime.episodes_aired) {
                    getString(R.string.episodes, anime.episodes)
                } else {
                    getString(R.string.episodes_of, anime.episodes_aired, anime.episodes)
                }
            } else {
                episodes.gone()
            }

            mpaaRating.text = when (anime.rating) {
                G -> getString(R.string.rating_g)
                NONE -> {
                    mpaaRating.gone()
                    ""
                }
                PG -> getString(R.string.rating_pg)
                PG_13 -> getString(R.string.rating_pg_13)
                R -> getString(R.string.rating_r)
                R_PLUS -> getString(R.string.rating_r_plus)
                RX -> getString(R.string.rating_rx)
                null -> {
                    mpaaRating.gone()
                    ""
                }
            }

            status.text = when (anime.status) {
                AnimeStatus.ANONS -> {
                    if (anime.aired_on != null) {
                        getString(R.string.anons_for, dateFormat.format(anime.aired_on))
                    } else {
                        getString(R.string.anons)
                    }
                }
                AnimeStatus.ONGOING -> {
                    if (anime.aired_on != null && anime.released_on != null) {
                        getString(
                            R.string.ongoing_from_to,
                            dateFormat.format(anime.aired_on),
                            dateFormat.format(anime.released_on)
                        )
                    } else if (anime.aired_on != null) {
                        getString(R.string.ongoing_from, dateFormat.format(anime.aired_on))
                    } else {
                        getString(R.string.ongoing)
                    }
                }
                AnimeStatus.RELEASED -> {
                    if (anime.released_on != null) {
                        getString(R.string.released_on, dateFormat.format(anime.released_on))
                    } else {
                        getString(R.string.released)
                    }
                }
            }

            anime.score.toFloat().let {
                if (it != 0f) {
                    scoreRatingBar.rating = it / 2
                } else {
                    scoreRatingBar.gone()
                }
            }

            if (anime.description == null) {
                expandDescriptionBtn.gone()
                description.text = getString(R.string.no_description)
            } else {
                description.text = anime.description
            }
//                userRating.text = it.user_rate?.score.toString()
//                userRatingBar.rating = it.user_rate?.score?.toFloat()?.div(2) ?: 0f


            if (anime.screenshots.isEmpty()) {
                screenshotsGroup.gone()
            } else {
                if (screenshots.adapter == null) {
                    screenshots.adapter = ScreenshotsAdapter(
                        this@AnimeFragment,
                        anime.screenshots,
                    )
                    screenshots.addItemDecoration(
                        HorizontalSpaceItemDecorator(
                            videos.context.dp(12),
                        ),
                    )
                } else {
                    (screenshots.adapter as ScreenshotsAdapter).setList(anime.screenshots)
                }
            }

            if (anime.videos.isEmpty()) {
                videosGroup.gone()
            } else {
                if (videos.adapter == null) {
                    videos.adapter = VideosAdapter(anime.videos)
                    videos.addItemDecoration(HorizontalSpaceItemDecorator(videos.context.dp(12)))
                } else {
                    (videos.adapter as VideosAdapter).setList(anime.videos)
                }
            }
        }

        if (anime.studios.isNotEmpty()) {
            anime.studios.map {
                binding.genresContainer.addView(createChip(it.name))
            }
        }

        if (anime.genres.isNotEmpty()) {
            anime.genres.map {
                binding.genresContainer.addView(createChip(it.russian))
            }
        } else {
            binding.genresContainer.gone()
        }

        if (anime.user_rate != null) {
            currentList = when (anime.user_rate.status) {
                PLANNED -> 0
                WATCHING -> 1
                REWATCHING -> 2
                COMPLETED -> 3
                ON_HOLD -> 4
                DROPPED -> 5
            }
        }
    }

    private fun onRolesUpdate(roles: List<Role>) {
        val listCharacters = roles.characters()
        val mainCharacters = listCharacters.mainCharacters()

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
//            binding.poster.id -> openScreenshot(arrayOf(posterUrl))

            binding.expandDescriptionBtn.id -> expandDescription()
        }
    }

    override fun onRatingChanged(v: RatingBar, rating: Float, fromUser: Boolean) {
//        binding.userRating.text = (rating * 2).toInt().toString()
//        TODO post rating
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAnimeBinding.inflate(inflater, container, false)
}