package com.ilfey.shikimori.base

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.ilfey.shikimori.di.AppSettings
import org.koin.android.ext.android.inject

abstract class BasePreferenceFragment : PreferenceFragmentCompat() {

    protected val settings: AppSettings by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.clipToPadding = false
    }
}