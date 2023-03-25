package com.ilfey.shikimori.ui.anime.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseBottomSheetDialogFragment
import com.ilfey.shikimori.databinding.SheetAnimeInfoBinding
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.utils.gone
import com.ilfey.shikimori.utils.withArgs

class InfoBottomSheet : BaseBottomSheetDialogFragment<SheetAnimeInfoBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = object {
            val kind = if (arguments?.getString(ARG_KIND) != null) {
                Kind.valueOf(arguments!!.getString(ARG_KIND)!!)
            } else {
                null
            }
            val studios = arguments?.getStringArray(ARG_STUDIOS)?.toList()
            val japanese = arguments?.getStringArray(ARG_JAPANESE)?.toList()
            val english = arguments?.getStringArray(ARG_ENGLISH)?.toList()
            val synonyms = arguments?.getStringArray(ARG_SYNONYMS)?.toList()
            val fandubbers = arguments?.getStringArray(ARG_FANDUBBERS)?.toList()
            val fansubbers = arguments?.getStringArray(ARG_FANSUBBERS)?.toList()
        }

        with(binding) {
            type.text = when (info.kind) {
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
            if (!info.studios.isNullOrEmpty()) {
                studio.text =
                    getString(R.string.studios, info.studios.joinToString { studio -> studio })
            } else {
                studio.gone()
            }
            if (!info.japanese.isNullOrEmpty()) {
                inJapanese.text =
                    getString(R.string.in_japanese, info.japanese.joinToString { s -> s!! })
            } else {
                inJapanese.gone()
            }
            if (!info.english.isNullOrEmpty()) {
                inEnglish.text =
                    getString(R.string.in_english, info.english.joinToString { s -> s!! })
            } else {
                inEnglish.gone()
            }
            if (!info.synonyms.isNullOrEmpty()) {
                altTitles.text =
                    getString(R.string.alt_titles, info.synonyms.joinToString { s -> s!! })
            } else {
                altTitles.gone()
            }
            if (!info.fandubbers.isNullOrEmpty()) {
                fandubbers.adapter = StringAdapter(info.fandubbers)
            } else {
                textViewFandubbers.gone()
                fandubbers.gone()
            }
            if (!info.fansubbers.isNullOrEmpty()) {
                fansubbers.adapter = StringAdapter(info.fansubbers)
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

    companion object {
        private const val TAG = "InfoBottomSheet"

        private const val ARG_KIND = "kind"
        private const val ARG_STUDIOS = "studios"
        private const val ARG_JAPANESE = "japanese"
        private const val ARG_ENGLISH = "english"
        private const val ARG_SYNONYMS = "synonyms"
        private const val ARG_FANDUBBERS = "fandubbers"
        private const val ARG_FANSUBBERS = "fansubbers"

        fun show(
            fm: FragmentManager,
            kind: Kind?,
            studios: Array<String>?,
            japanese: Array<String>?,
            english: Array<String>?,
            synonyms: Array<String>?,
            fandubbers: Array<String>?,
            fansubbers: Array<String>?,
        ) {
            InfoBottomSheet().withArgs(7) {
                putString(ARG_KIND, kind?.name)
                putStringArray(ARG_STUDIOS, studios)
                putStringArray(ARG_JAPANESE, japanese)
                putStringArray(ARG_ENGLISH, english)
                putStringArray(ARG_SYNONYMS, synonyms)
                putStringArray(ARG_FANDUBBERS, fandubbers)
                putStringArray(ARG_FANSUBBERS, fansubbers)
            }.show(fm, TAG)
        }
    }
}