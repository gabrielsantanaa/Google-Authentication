package com.gabrielsantana.letsvote.features.poll.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class QuestionUiModel(
    val title: String,
    val options: List<String>,
    val correctOptionIndex: Int
) : Parcelable