package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.CheckRequestDto
import com.example.myapplication.data.repository.CheckRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CheckRequestUiState(
    val isLoading: Boolean = false,
    val checkRequest: CheckRequestDto? = null,
    val isSubmitting: Boolean = false,
    val message: String? = null
)

class CheckRequestViewModel : ViewModel() {

    private val repository = CheckRequestRepository()

    private val _uiState = MutableStateFlow(CheckRequestUiState())
    val uiState: StateFlow<CheckRequestUiState> = _uiState.asStateFlow()

    init {
        loadCurrent()
    }

    fun loadCurrent() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                message = null
            )

            try {
                val result = repository.fetchCurrent(1)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    checkRequest = result
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "取得に失敗しました"
                )
            }
        }
    }

    fun confirm() {
        val current = _uiState.value.checkRequest ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSubmitting = true
            )

            try {
                repository.confirm(current.id.toLong())

                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    message = "服薬を記録しました"
                )

                loadCurrent()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    message = "送信に失敗しました"
                )
            }
        }
    }
}