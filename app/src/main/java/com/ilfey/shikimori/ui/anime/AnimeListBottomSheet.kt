package com.ilfey.shikimori.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ilfey.shikimori.base.BaseBottomSheetDialogFragment
import com.ilfey.shikimori.databinding.SheetAnimeListBinding

class AnimeListBottomSheet() : BaseBottomSheetDialogFragment<SheetAnimeListBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.recyclerViewLists.adapter =
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = SheetAnimeListBinding.inflate(inflater, container, false)
}