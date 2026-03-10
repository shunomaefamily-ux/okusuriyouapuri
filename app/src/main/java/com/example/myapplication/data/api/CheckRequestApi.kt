package com.example.myapplication.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CheckRequestApi {

    @GET("api/check_request/current")
    suspend fun getCurrentCheckRequest(): CurrentCheckRequestResponse

    @POST("api/check_requests/{id}/confirm")
    suspend fun confirmCheckRequest(
        @Path("id") id: Long,
        @Body request: ConfirmRequest
    ): ConfirmResponse
}