package com.gabrielsantana.letsvote.features.poll.domain.models

data class PoolQuestionEntityModel(
    val title: String,
    val options: List<String>,
    val correctOptionIndex: Int
)
