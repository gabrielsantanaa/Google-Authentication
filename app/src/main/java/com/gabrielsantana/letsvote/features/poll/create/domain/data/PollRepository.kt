package com.gabrielsantana.letsvote.features.poll.create.domain.data

import com.gabrielsantana.letsvote.features.poll.create.domain.data.models.PollDataModel
import com.gabrielsantana.letsvote.features.poll.create.domain.data.models.PollQuestionDataModel

interface PollRepository {
    suspend fun findPollByCode(code: String): PollDataModel?
    suspend fun createPoll(
        roomCode: String,
        title: String,
        questions: List<PollQuestionDataModel>
    ): PollDataModel
}