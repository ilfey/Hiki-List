package com.ilfey.shikimori.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding> : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    protected open fun bindViewModel() {}

    protected abstract fun onInflateView(inflater: LayoutInflater, container: ViewGroup?): B

    protected fun toast(text: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(), text, duration).show()

    protected fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(), resId, duration).show()
}