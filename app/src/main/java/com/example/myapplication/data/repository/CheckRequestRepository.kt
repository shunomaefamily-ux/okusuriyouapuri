package com.example.myapplication.data.repository

import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.CheckRequestDto
import com.example.myapplication.data.api.ConfirmRequest
import com.example.myapplication.data.api.PersonDto

class CheckRequestRepository {

    private val api = ApiClient.api

    suspend fun fetchPeople(): List<PersonDto> {
        return api.getPeople().people
    }

    suspend fun fetchCurrent(personId: Long): CheckRequestDto? {
        val response = api.getCurrentCheckRequest(personId)
        return response.check_request
    }

    suspend fun confirm(id: Long, slot: String) {
        api.confirmCheckRequest(
            id = id,
            request = ConfirmRequest(
                source = "android_app",
                slot = slot
            )
        )
    }
}