package com.gabrielsantana.letsvote.screens.question

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewQuestionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewQuestionUiState.INITIAL)
    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun addOption() {
        if (_uiState.value.newOptionText.isEmpty()) return
        val option = _uiState.value.newOptionText
        _uiState.update { it.copy(newOptionText = "") }
        _uiState.value = _uiState.value.copy(options = _uiState.value.options + option)
    }

    fun removeOption(option: Int) {
        _uiState.value = _uiState.value.copy(options = _uiState.value.options - _uiState.value.options[option])
    }

    fun updateNewOptionText(newOptionText: String) {
        _uiState.value = _uiState.value.copy(newOptionText = newOptionText)
    }

    fun updateCorrectOptionIndex(correctOptionIndex: Int) {
        _uiState.value = _uiState.value.copy(correctOptionIndex = correctOptionIndex)
    }


}