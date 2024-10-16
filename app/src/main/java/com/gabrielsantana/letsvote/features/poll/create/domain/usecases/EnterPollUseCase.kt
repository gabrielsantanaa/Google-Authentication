package com.gabrielsantana.letsvote.features.poll.create.domain.usecases

import com.gabrielsantana.letsvote.features.poll.create.domain.data.models.PollDataModel
import com.gabrielsantana.letsvote.features.user.domain.models.UserEntityModel
import javax.inject.Inject

class EnterPollUseCase @Inject constructor() {
    suspend operator fun invoke(user: UserEntityModel, pollCode: String): PollDataModel? {
        TODO("Not implemented yet")
    }
}