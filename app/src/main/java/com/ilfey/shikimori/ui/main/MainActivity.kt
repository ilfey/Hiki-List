package com.ilfey.shikimori.ui.main

import android.os.Bundle
import androidx.fragment.app.commit
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseActivity
import com.ilfey.shikimori.databinding.ActivityMainBinding
import com.ilfey.shikimori.ui.auth.AuthFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            if (settings.isAuthorized) {
                add(R.id.container, MainFragment.newInstance())
            } else {
                add(R.id.container, AuthFragment.newInstance())
            }
            disallowAddToBackStack()
        }
    }

    override fun onInflateView() = ActivityMainBinding.inflate(layoutInflater)
}