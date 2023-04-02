package com.ilfey.shikimori.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.preference.PreferenceFragmentCompat
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseActivity
import com.ilfey.shikimori.databinding.ActivitySettingsBinding
import com.ilfey.shikimori.utils.addBackButton

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.addBackButton { onBackPressedDispatcher.onBackPressed() }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            openDefaultFragment()
        }
    }

    private fun openDefaultFragment() {
        val fragment = when (intent?.action) {
            // TODO create intends
            else -> SettingsFragment.newInstance()
        }
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, fragment)
        }
    }

    override fun onInflateView() = ActivitySettingsBinding.inflate(layoutInflater)

    companion object {
        fun newIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }
}