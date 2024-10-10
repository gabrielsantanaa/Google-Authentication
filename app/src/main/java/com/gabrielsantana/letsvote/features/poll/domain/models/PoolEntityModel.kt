package com.gabrielsantana.letsvote.features.poll.domain.models

data class PoolEntityModel(
    val code: String,
    val title: String,
    val questions: List<PoolQuestionEntityModel>
)