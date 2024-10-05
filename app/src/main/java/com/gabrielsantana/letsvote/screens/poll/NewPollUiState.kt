package com.gabrielsantana.letsvote.screens.poll

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

data class QuestionUiModel(
    val title: String,
    val options: List<String>,
    val correctOptionIndex: Int
)