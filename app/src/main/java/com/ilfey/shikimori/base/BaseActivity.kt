package com.ilfey.shikimori.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.AppSettings
import org.koin.android.ext.android.inject

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    protected val settings: AppSettings by inject()
    protected val binding: B
        get() = checkNotNull(viewBinding)

    private var viewBinding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme()

        val binding = onInflateView()
        viewBinding = binding

        setContentView(binding.root)
    }

    protected fun setTheme() = setTheme(settings.theme)

    protected fun switchTheme() {
        settings.theme = when(settings.theme) {
            R.style.AppTheme_Dark -> R.style.AppTheme_Light
            R.style.AppTheme_Light -> R.style.AppTheme_Dark
            else -> R.style.AppTheme_Light
        }
    }

    protected fun toast(text: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration).show()

    protected abstract fun onInflateView(): B
}