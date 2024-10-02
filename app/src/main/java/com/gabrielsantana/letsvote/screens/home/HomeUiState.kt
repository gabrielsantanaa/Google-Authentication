package com.gabrielsantana.letsvote.screens.home

data class HomeUiState(
    val roomCode: String = ""
) {
    companion object {
        val INITIAL = HomeUiState()
    }
}