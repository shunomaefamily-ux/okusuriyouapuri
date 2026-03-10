package com.example.myapplication.data.repository

import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.CheckRequestDto
import com.example.myapplication.data.api.ConfirmRequest

class CheckRequestRepository {

    private val api = ApiClient.api

    suspend fun fetchCurrent(): CheckRequestDto? {
        val response = api.getCurrentCheckRequest()
        return response.check_request
    }

    suspend fun confirm(id: Long) {
        api.confirmCheckRequest(
            id = id,
            request = ConfirmRequest(
                source = "android_app"
            )
        )
    }
}