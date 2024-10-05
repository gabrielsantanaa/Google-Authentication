package com.gabrielsantana.letsvote.screens.poll

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NewPollViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewPollUiState.INITIAL)
    val uiState = _uiState

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun addQuestion(question: QuestionUiModel) {
        _uiState.update { it. copy(questions = _uiState.value.questions + question) }
    }

}