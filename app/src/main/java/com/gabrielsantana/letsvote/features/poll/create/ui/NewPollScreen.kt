@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gabrielsantana.letsvote.features.poll.create.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NewPollScreen(
    onNavigateBack: () -> Unit,
    onAddQuestion: () -> Unit,
    viewModel: NewPollViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewPollContent(
        uiState = uiState,
        onBack = onNavigateBack,
        onSave = viewModel::createPoll,
        onSettings = {
            // TODO: Open settings
        },
        onAddQuestion = onAddQuestion,
        onRemoveQuestion = viewModel::removeQuestion,
        onChangeTitle = viewModel::updateTitle
    )
}

@Composable
fun NewPollContent(
    uiState: NewPollUiState,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onSettings: () -> Unit,
    onAddQuestion: () -> Unit,
    onRemoveQuestion: (model: QuestionUiModel) -> Unit,
    onChangeTitle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Poll") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Go back")
                    }
                },
                actions = {
                    Button(onClick = onSave) {
                        Text("Create")
                    }
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Open Poll settings")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddQuestion,
                text = { Text("Add question") },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add question") }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = onChangeTitle,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Text("Questions", style = MaterialTheme.typography.titleMedium)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.questions.forEachIndexed { index, question ->
                    QuestionItem(
                        question = question,
                        onDelete = { onRemoveQuestion(question) }
                    )
                }
            }
        }

    }
}

@Composable
fun QuestionItem(
    question: QuestionUiModel,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = modifier
                    .weight(1F)
                    .fillMaxWidth()
            ) {
                Text(question.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                question.options.forEachIndexed { index, option ->
                    Row {
                        Text(option, style = MaterialTheme.typography.bodySmall)
                        if (index == question.correctOptionIndex) {
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Correct answer",
                                modifier = Modifier.size(MaterialTheme.typography.bodySmall.lineHeight.value.dp)
                            )
                        }
                    }
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Outlined.Delete, contentDescription = "Delete question")
            }
        }
    }
}

@Preview
@Composable
private fun NewPollContentPreview() {
    MaterialTheme {
        NewPollContent(
            NewPollUiState.INITIAL,
            onBack = {},
            onSave = {},
            onSettings = {},
            onAddQuestion = {},
            onRemoveQuestion = {},
            onChangeTitle = {}
        )
    }
}

private val questions = listOf(
    QuestionUiModel("When i was born?", listOf("1990", "1991", "1992", "1993"), 2),
    QuestionUiModel("What is my favorite color?", listOf("Red", "Blue", "Green", "Yellow"), 1),
)