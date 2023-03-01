package com.ilfey.shikimori.di.network

import android.net.Uri
import com.ilfey.shikimori.BuildConfig
import net.openid.appauth.*
import kotlin.coroutines.suspendCoroutine

class Authenticator(
    private val storage: Storage,
) {
    companion object {
        private const val AUTH_URL = "https://shikimori.one/oauth/authorize"
        private const val TOKEN_URL = "https://shikimori.one/oauth/token"
        private const val CALLBACK_URL = "${BuildConfig.APPLICATION_ID}://oauth/shikimori"
        private const val SCOPE = ""
        private const val RESPONSE_TYPE = ResponseTypeValues.CODE
    }

    private val clientAuthentication = ClientSecretPost(BuildConfig.CLIENT_SECRET)
    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AUTH_URL),
        Uri.parse(TOKEN_URL),
    )

    fun getAuthRequest(): AuthorizationRequest {
        return AuthorizationRequest.Builder(
            serviceConfiguration,
            BuildConfig.CLIENT_ID,
            RESPONSE_TYPE,
            Uri.parse(CALLBACK_URL)
        )
            .setScope(SCOPE)
            .setCodeVerifier(null)
            .build()
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            BuildConfig.CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) : Boolean {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(tokenRequest, clientAuthentication) { response, ex ->
                when {
                    response != null -> {
                        //получение токена произошло успешно

                        storage.accessToken = response.accessToken.orEmpty()
                        storage.refreshToken = response.refreshToken.orEmpty()
//                        idToken

                        continuation.resumeWith(Result.success(true))
                    }
                    //получение токенов произошло неуспешно, показываем ошибку
                    ex != null -> continuation.resumeWith(Result.failure(ex))
                    else -> error("unreachable")
                }
            }
        }
    }


}