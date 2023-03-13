package com.ilfey.shikimori.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<B : ViewBinding> : BottomSheetDialogFragment() {

    private var viewBinding: B? = null

    protected val binding: B
        get() = checkNotNull(viewBinding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = onInflateView(inflater, container)
        viewBinding = binding

        return binding.root
    }

    protected abstract fun onInflateView(inflater: LayoutInflater, container: ViewGroup?): B
}