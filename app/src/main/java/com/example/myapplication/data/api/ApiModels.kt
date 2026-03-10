package com.example.myapplication.data.api

data class CurrentCheckRequestResponse(
    val check_request: CheckRequestDto?
)

data class CheckRequestDto(
    val id: Long,
    val title: String,
    val person_name: String,
    val scheduled_at: String,
    val items: List<CheckRequestItemDto>
)

data class CheckRequestItemDto(
    val name: String,
    val dose_amount: String,
    val dose_unit: String
)

data class ConfirmRequest(
    val source: String
)

data class ConfirmResponse(
    val success: Boolean = true
)