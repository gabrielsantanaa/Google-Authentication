package com.gabrielsantana.letsvote.features.poll.create.ui.model

import android.os.Parcelable
import com.gabrielsantana.letsvote.features.poll.create.domain.data.models.PollQuestionDataModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionUiModel(
    val title: String,
    val options: List<String>,
    val correctOptionIndex: Int
) : Parcelable

fun QuestionUiModel.toDataModel() = PollQuestionDataModel(
    title = title,
    options = options,
    correctOptionIndex = correctOptionIndex,
    votes = 0,
)