package com.gabrielsantana.letsvote.features.poll.create.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielsantana.letsvote.features.poll.create.domain.data.PollRepository
import com.gabrielsantana.letsvote.features.poll.create.ui.model.QuestionUiModel
import com.gabrielsantana.letsvote.features.poll.create.ui.model.toDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

@HiltViewModel
class NewPollViewModel @Inject constructor(
    private val pollRepository: PollRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(NewPollUiState.INITIAL)
    val uiState = _uiState

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun addQuestion(question: QuestionUiModel) {
        _uiState.update { it.copy(questions = _uiState.value.questions + question) }
    }

    fun createPoll() {
        val isTitleValid = _uiState.value.title.isNotBlank() //TODO what's the validation rule?
        val isQuestionsValid = _uiState.value.questions.isNotEmpty()

        if (isTitleValid && isQuestionsValid) {
            viewModelScope.launch {
                pollRepository.createPoll(
                    roomCode = (100000..999999).random(Random(System.currentTimeMillis())).toString(),
                    title = _uiState.value.title,
                    questions = _uiState.value.questions.map { it.toDataModel() }
                )
            }
        } else {
            //TODO ui error state
        }
    }

    fun removeQuestion(question: QuestionUiModel) {
        _uiState.update { it.copy(questions = _uiState.value.questions - question) }
    }

}