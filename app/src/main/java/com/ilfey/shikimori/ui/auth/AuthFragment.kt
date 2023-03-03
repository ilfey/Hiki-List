package com.ilfey.shikimori.ui.auth

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentAuthBinding
import com.ilfey.shikimori.utils.launchAndCollectIn
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import org.koin.android.ext.android.inject

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val viewModel by inject<AuthViewModel>()

    override fun bindViewModel() {
        binding.signInButton.setOnClickListener {
            viewModel.openLoginPage()
        }
        viewModel.isAuthorizedFlow.launchAndCollectIn(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
//                updateIsLoading(it)
//                viewModel.onAuthCodeReceived()
            }
        }

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
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
        }
    }

    private val getAuthResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val dataIntent = it.data ?: return@registerForActivityResult
        handleAuthResponseIntent(dataIntent)
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        signInButton.isVisible = !isLoading
        loginProgress.isVisible = isLoading
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

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater)
}