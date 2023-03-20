package com.ilfey.shikimori.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseBottomSheetDialogFragment
import com.ilfey.shikimori.databinding.SheetAnimeInfoBinding
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.utils.gone

class AnimeInfoBottomSheet : BaseBottomSheetDialogFragment<SheetAnimeInfoBinding>() {

    private val args by navArgs<AnimeInfoBottomSheetArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            type.text = when (args.anime.kind) {
                Kind.TV -> getString(R.string.type_tv)
                Kind.MOVIE -> getString(R.string.type_movie)
                Kind.OVA -> getString(R.string.type_ova)
                Kind.ONA -> getString(R.string.type_ona)
                Kind.SPECIAL -> getString(R.string.type_special)
                Kind.MUSIC -> getString(R.string.type_music)
                Kind.TV_13 -> getString(R.string.type_tv)
                Kind.TV_24 -> getString(R.string.type_tv)
                Kind.TV_48 -> getString(R.string.type_tv)
                else -> {
                    type.gone()
                    ""
                }
            }
            if (args.anime.studios.isNotEmpty()){
                studio.text = getString(R.string.studios, args.anime.studios.joinToString { studio -> studio.name })
            } else {
                studio.gone()
            }
            if (args.anime.japanese.isNotEmpty() && null !in args.anime.japanese) {
                inJapanese.text = getString(R.string.in_japanese, args.anime.japanese.joinToString { s -> s!! })
            } else {
                inJapanese.gone()
            }
            if (args.anime.english.isNotEmpty() && null !in args.anime.english) {
                inEnglish.text = getString(R.string.in_english, args.anime.english.joinToString { s -> s!! })
            } else {
                inEnglish.gone()
            }
            if (args.anime.synonyms.isNotEmpty() && null !in args.anime.synonyms){
                altTitles.text = getString(R.string.alt_titles, args.anime.synonyms.joinToString { s -> s!! })
            } else {
                altTitles.gone()
            }
            if (args.anime.fandubbers.isNotEmpty()){
                fandubbers.adapter = StringAdapter(args.anime.fandubbers)
            } else {
                textViewFandubbers.gone()
                fandubbers.gone()
            }
            if (args.anime.fandubbers.isNotEmpty()) {
                fansubbers.adapter = StringAdapter(args.anime.fansubbers)
            } else {
                textViewFansubbers.gone()
                fansubbers.gone()
            }
        }
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = SheetAnimeInfoBinding.inflate(inflater, container, false)
}