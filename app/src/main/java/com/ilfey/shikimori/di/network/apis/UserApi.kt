package com.ilfey.shikimori.di.network.apis

import com.ilfey.shikimori.di.network.entities.AnimeRate
import com.ilfey.shikimori.di.network.entities.Favorites
import com.ilfey.shikimori.di.network.entities.HistoryItem
import com.ilfey.shikimori.di.network.entities.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    /**
     * List users
     * See: https://shikimori.one/api/doc/1.0/users/index
     * */
//    @GET("/api/users")
//    fun users(): Call<*>

    /**
     * Show an user
     * See: https://shikimori.one/api/doc/1.0/users/show
     * */
//    @GET("/api/users/{id}")
//    fun user(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * Show an user
     * See: https://shikimori.one/api/doc/1.0/users/show
     * */
//    @GET("/api/users/{id}")
//    fun user(
//        @Path("id") id: String,
//    ): Call<*>

    /**
     * Show user's brief info
     * See: https://shikimori.one/api/doc/1.0/users/info
     * */
//    @GET("/api/users/{id}/info")
//    fun info(
//        @Path("id") id: String,
//    ): Call<*>

    /**
     * Show current user's brief info.
     * See: https://shikimori.me/api/doc/1.0/users/whoami
     * */
    @GET("/api/users/whoami")
    fun whoami(): Call<User>

    /**
     * Sign out the user
     * See: https://shikimori.one/api/doc/1.0/users/sign_out
     * */
//    @GET("/api/users/{id}/sing_out")
//    fun sign_out(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * Sign out the user
     * See: https://shikimori.one/api/doc/1.0/users/sign_out
     * */
//    @GET("/api/users/{id}/sing_out")
//    fun sign_out(
//        @Path("id") id: String,
//    ): Call<*>

    /**
     * Show user's friends
     * See: https://shikimori.one/api/doc/1.0/users/friends
     * */
//    @GET("/api/users/{id}/friends")
//    fun friends(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * Show user's friends
     * See: https://shikimori.one/api/doc/1.0/users/friends
     * */
//    @GET("/api/users/{id}/friends")
//    fun friends(
//        @Path("id") id: String,
//    ): Call<*>

    /**
     * Show user's clubs
     * See: https://shikimori.one/api/doc/1.0/users/clubs
     * */
//    @GET("/api/users/{id}/clubs")
//    fun clubs(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * Show user's clubs
     * See: https://shikimori.one/api/doc/1.0/users/clubs
     * */
//    @GET("/api/users/{id}/clubs")
//    fun clubs(
//        @Path("id") id: String,
//    ): Call<*>

    /**
     * Show user's anime list
     * See: https://shikimori.me/api/doc/1.0/users/anime_rates
     * */
    @GET("/api/users/{id}/anime_rates")
    fun animeRates(
        @Path("id") id: Long,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 5000,
        /**
         * ListTypes
         * */
        @Query("status") status: String? = null,
        @Query("censored") censored: Boolean? = null,
    ): Call<List<AnimeRate>>

    /**
     * Show user's anime list
     * See: https://shikimori.me/api/doc/1.0/users/anime_rates
     * */
    @GET("/api/users/{id}/anime_rates")
    fun animeRates(
        @Path("id") id: String,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 5000,
        /**
         * ListTypes
         * */
        @Query("status") status: String? = null,
        @Query("censored") censored: Boolean? = null,
    ): Call<List<AnimeRate>>

    /**
     * 	Show user's manga list
     * See: https://shikimori.me/api/doc/1.0/users/manga_rates
     * */
//    @GET("/api/users/{id}/manga")
//    fun manga_rates(
//        @Path("id") id: Long,
//        @Query("page") page: Int? = null,
//        @Query("limit") limit: Int = 5000,
//        /**
//         * ListTypes
//         * */
//        @Query("censored") censored: Boolean? = null,
//    ): Call<List<AnimeRate>>

    /**
     * 	Show user's manga list
     * See: https://shikimori.me/api/doc/1.0/users/manga_rates
     * */
//    @GET("/api/users/{id}/manga")
//    fun manga_rates(
//        @Path("id") id: String,
//        @Query("page") page: Int? = null,
//        @Query("limit") limit: Int = 5000,
//        /**
//         * ListTypes
//         * */
//        @Query("censored") censored: Boolean? = null,
//    ): Call<List<AnimeRate>>

    /**
     * Show user's favorites
     * See: https://shikimori.me/api/doc/1.0/users/favourites
     * */
    @GET("/api/users/{id}/favourites")
    fun favorites(
        @Path("id") id: Long,
    ): Call<Favorites>

    /**
     * Show user's favorites
     * See: https://shikimori.me/api/doc/1.0/users/favourites
     * */
    @GET("/api/users/{id}/favourites")
    fun favorites(
        @Path("id") id: String,
    ): Call<Favorites>


    /**
     * Show current user's messages
     * Requires messages oauth scope
     * See: https://shikimori.one/api/doc/1.0/users/messages
     */
//    @GET("/api/users/{id}/messages")
//    fun messages(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * Show current user's messages
     * Requires messages oauth scope
     * See: https://shikimori.one/api/doc/1.0/users/messages
     */
//    @GET("/api/users/{id}/messages")
//    fun messages(
//        @Path("id") id: String,
//    ): Call<*>

    /**
     * Show current user's unread messages counts
     * Requires messages oauth scope
     * See: https://shikimori.one/api/doc/1.0/users/unread_messages
     */
//    @GET("/api/users/{id}/unread_messages")
//    fun messages(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * Show user history
     * See: https://shikimori.me/api/doc/1.0/users/history
     * */
    @GET("/api/users/{id}/history")
    fun history(
        @Path("id") id: String,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 100,
        @Query("target_id") target_id: Long? = null,
        @Query("target_type") target_type: String? = null,
    ): Call<List<HistoryItem>>

    /**
     * Show current user's unread messages counts
     * Requires messages oauth scope
     * See: https://shikimori.one/api/doc/1.0/users/unread_messages
     */
//    @GET("/api/users/{id}/unread_messages")
//    fun messages(
//        @Path("id") id: String,
//    ): Call<*>

    /**
     * Show user's bans
     * See: https://shikimori.one/api/doc/1.0/users/bans
     */
//    @GET("/api/users/{id}/bans")
//    fun messages(
//        @Path("id") id: String,
//    ): Call<*>


    /*    API V2    */


//    TODO: Add v2 user endpoinds
}