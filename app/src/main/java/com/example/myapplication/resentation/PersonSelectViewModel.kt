package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.PersonDto
import com.example.myapplication.data.repository.CheckRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PersonSelectUiState(
    val isLoading: Boolean = false,
    val people: List<PersonDto> = emptyList(),
    val message: String? = null
)

class PersonSelectViewModel : ViewModel() {

    private val repository = CheckRequestRepository()

    private val _uiState = MutableStateFlow(PersonSelectUiState())
    val uiState: StateFlow<PersonSelectUiState> = _uiState.asStateFlow()

    init {
        loadPeople()
    }

    fun loadPeople() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                message = null
            )

            try {
                val result = repository.fetchPeople()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    people = result
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "利用者一覧の取得に失敗しました"
                )
            }
        }
    }
}