package com.ilfey.shikimori.ui.main

import com.ilfey.shikimori.base.BaseActivity
import com.ilfey.shikimori.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onInflateView() = ActivityMainBinding.inflate(layoutInflater)
}