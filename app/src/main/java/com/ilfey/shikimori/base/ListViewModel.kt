package com.ilfey.shikimori.base

import androidx.lifecycle.ViewModel

abstract class ListViewModel : ViewModel() {
    abstract fun onRefresh()
}