package com.gabrielsantana.letsvote.screens.question

import com.gabrielsantana.letsvote.features.poll.ui.QuestionUiModel

data class NewQuestionUiState(
    val title: String = "",
    val options: List<String> = emptyList(),
    val newOptionText: String = "",
    val correctOptionIndex: Int = 0,
) {
    companion object {
        val INITIAL: NewQuestionUiState
            get() = NewQuestionUiState()
    }
}

fun NewQuestionUiState.toQuestionUiModel(): QuestionUiModel {
    return QuestionUiModel(
        title = title,
        options = options,
        correctOptionIndex = correctOptionIndex
    )
}