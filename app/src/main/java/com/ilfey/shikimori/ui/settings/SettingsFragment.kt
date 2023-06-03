package com.ilfey.shikimori.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BasePreferenceFragment
import androidx.preference.SwitchPreferenceCompat
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.ui.main.MainActivity
import com.ilfey.shikimori.utils.launchAndCollectIn
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SettingsFragment : BasePreferenceFragment() {

    private val storage by inject<Storage>()
    private val viewModel by activityViewModel<SettingsViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_root, rootKey)

        findPreference<SwitchPreferenceCompat>(AppSettings.KEY_SHOW_ACTIONS).run {
            this?.isChecked = settings.showActions
        }

        findPreference<Preference>(AppSettings.KEY_APP_VERSION)?.run {
            title = getString(R.string.app_version, BuildConfig.VERSION_NAME)
        }

        findPreference<Preference>("logout")?.setOnPreferenceClickListener {
            viewModel.logout()
            true
        }
    }

    override fun bindViewModel() {
        viewModel.logoutSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
            if (it) {
                // Clear cache
                settings.clear()
                storage.clear()

                // Restart app
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
//                exitProcess(0)
            }
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}