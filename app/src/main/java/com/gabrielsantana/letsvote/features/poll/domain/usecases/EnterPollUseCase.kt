package com.gabrielsantana.letsvote.features.poll.domain.usecases

import com.gabrielsantana.letsvote.features.poll.domain.models.PoolEntityModel
import com.gabrielsantana.letsvote.features.user.domain.models.UserEntityModel
import javax.inject.Inject

class EnterPollUseCase @Inject constructor() {
    suspend operator fun invoke(user: UserEntityModel, pollCode: String): PoolEntityModel? {
        TODO("Not implemented yet")
    }
}