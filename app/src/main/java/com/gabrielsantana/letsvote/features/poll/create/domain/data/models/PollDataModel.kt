package com.gabrielsantana.letsvote.features.poll.create.domain.data.models

data class PollDataModel(
    val code: String,
    val title: String,
    val questions: List<PollQuestionDataModel>
)