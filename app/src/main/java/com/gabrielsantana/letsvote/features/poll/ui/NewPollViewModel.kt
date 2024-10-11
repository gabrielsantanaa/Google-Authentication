package com.gabrielsantana.letsvote.features.poll.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewPollViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(NewPollUiState.INITIAL)
    val uiState = _uiState

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun addQuestion(question: QuestionUiModel) {
        _uiState.update { it.copy(questions = _uiState.value.questions + question) }
    }

    fun onSave() {

    }

    fun removeQuestion(question: QuestionUiModel) {
        _uiState.update { it.copy(questions = _uiState.value.questions - question) }
    }

}