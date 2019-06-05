package com.bypassmobile.octo.rest

import com.bypassmobile.octo.model.User
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubEndpoint {

    @GET("/users/{id}")
    fun getUser(@Path("id") user: String): Single<User>

    @GET("/users/{id}/following")
    fun getFollowingUser(@Path("id") user: String): Single<List<User>>

    @GET("/orgs/{id}/members")
    fun getOrganizationMember(@Path("id") organization: String): Single<List<User>>

    companion object {
        val SERVER = "https://api.github.com"
    }
}
