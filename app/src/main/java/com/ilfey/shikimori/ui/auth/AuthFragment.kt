package com.ilfey.shikimori.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.commit
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentAuthBinding
import com.ilfey.shikimori.di.network.enums.Locale
import com.ilfey.shikimori.ui.main.MainFragment
import com.ilfey.shikimori.utils.invisible
import com.ilfey.shikimori.utils.launchAndCollectIn
import com.ilfey.shikimori.utils.visible
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment<FragmentAuthBinding>(), View.OnClickListener {

    private val viewModel by viewModel<AuthViewModel>()
    private val customTabsIntent = CustomTabsIntent.Builder().build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInButton.setOnClickListener(this)
        binding.signUpButton.setOnClickListener(this)
    }

    override fun bindViewModel() {
        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateIsLoading(it)
        }
        viewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }
        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }
        viewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
            settings.isAuthorized = true
            viewModel.currentUser()
        }
        viewModel.user.observe(viewLifecycleOwner) {
            settings.userId = it.id
            settings.isEnLocale = it.locale == Locale.EN
            settings.username = it.username

            parentFragmentManager.commit {
                replace(R.id.container, MainFragment.newInstance())
                disallowAddToBackStack()
            }
        }
    }

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            handleAuthResponseIntent(dataIntent)
        }

    private fun updateIsLoading(isLoading: Boolean) = if (isLoading) {
        binding.content.invisible()
        binding.loginProgress.show()
    } else {
        binding.content.visible()
        binding.loginProgress.hide()
    }


    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            exception != null -> viewModel.onAuthCodeFailed(exception)
            tokenExchangeRequest != null ->
                viewModel.onAuthCodeReceived(tokenExchangeRequest)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sign_in_button -> viewModel.openLoginPage()
            R.id.sign_up_button -> customTabsIntent.launchUrl(
                v.context,
                Uri.parse(BuildConfig.APP_URL + "/users/sign_up")
            )
        }
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater)

    companion object {
        fun newInstance() = AuthFragment()
    }
}