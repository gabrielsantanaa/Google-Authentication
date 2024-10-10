package com.gabrielsantana.letsvote.features.poll.data

import com.gabrielsantana.letsvote.features.poll.domain.models.PoolEntityModel

interface PollRepository {
    suspend fun findPollByCode(code: String): PoolEntityModel?
}