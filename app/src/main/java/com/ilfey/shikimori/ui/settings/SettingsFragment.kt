package com.ilfey.shikimori.ui.settings

import android.os.Bundle
import androidx.preference.Preference
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BasePreferenceFragment
import androidx.preference.SwitchPreferenceCompat
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.di.AppSettings

class SettingsFragment : BasePreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_root, rootKey)

        findPreference<SwitchPreferenceCompat>(AppSettings.KEY_SHOW_ACTIONS).run {
            this?.isChecked = settings.showActions
        }

        findPreference<Preference>(AppSettings.KEY_APP_VERSION)?.run {
            title = getString(R.string.app_version, BuildConfig.VERSION_NAME)
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}