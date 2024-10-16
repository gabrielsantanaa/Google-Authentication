package com.gabrielsantana.letsvote.features.poll.create.domain.data.models

data class PollQuestionDataModel(
    val title: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val votes: Int,
)
