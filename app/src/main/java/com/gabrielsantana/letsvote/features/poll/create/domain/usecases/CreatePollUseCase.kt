package com.gabrielsantana.letsvote.features.poll.create.domain.usecases

import com.gabrielsantana.letsvote.features.poll.create.domain.data.PollRepository
import javax.inject.Inject

class CreatePollUseCase @Inject constructor(
    private val pollRepository: PollRepository
) {

}