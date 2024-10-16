package com.gabrielsantana.letsvote.features.poll.create.ui

import com.gabrielsantana.letsvote.features.poll.create.ui.model.QuestionUiModel

data class NewPollUiState(
    val title: String = "",
    val questions: List<QuestionUiModel> = emptyList(),
    val newQuestion: QuestionUiModel? = null
) {
    companion object {
        val INITIAL: NewPollUiState
            get() = NewPollUiState()
    }
}

