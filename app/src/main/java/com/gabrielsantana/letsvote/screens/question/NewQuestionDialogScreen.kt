package com.gabrielsantana.letsvote.screens.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielsantana.letsvote.features.poll.ui.QuestionUiModel

@Composable
fun NewQuestionDialogScreen(
    onNavigateBack: () -> Unit,
    onSave: (model: QuestionUiModel) -> Unit,
    viewModel: NewQuestionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    NewQuestionDialogContent(
        onDismissRequest = onNavigateBack,
        uiState = uiState,
        onSave = {
            onSave(uiState.toQuestionUiModel())
        },
        onTitleChange = viewModel::updateTitle,
        onNewOptionTextChange = viewModel::updateNewOptionText,
        onAddNewOption = viewModel::addOption,
        onChangeCorrectOptionIndex = viewModel::updateCorrectOptionIndex,
        onRemoveOption = viewModel::removeOption
    )
}

@Composable
fun NewQuestionDialogContent(
    uiState: NewQuestionUiState,
    onDismissRequest: () -> Unit,
    onTitleChange: (String) -> Unit,
    onNewOptionTextChange: (String) -> Unit,
    onSave: () -> Unit,
    onAddNewOption: () -> Unit,
    onChangeCorrectOptionIndex: (Int) -> Unit,
    onRemoveOption: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest) {
        Card(
            modifier = modifier
                .clip(MaterialTheme.shapes.large)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    label = { Text("Question title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(Modifier.selectableGroup()) {
                    uiState.options.forEachIndexed { index, text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (index == uiState.correctOptionIndex),
                                    onClick = { onChangeCorrectOptionIndex(index) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (index == uiState.correctOptionIndex),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            Spacer(Modifier.weight(1F))
                            IconButton(onClick = { onRemoveOption(index) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete option")
                            }
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = uiState.newOptionText,
                        onValueChange = onNewOptionTextChange,
                        label = { Text("New option") },
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                    )
                    FilledIconButton(onClick = onAddNewOption) {
                        Icon(Icons.Default.Add, contentDescription = "Add option")
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(onClick = onSave, Modifier.fillMaxWidth()) {
                    Text("Save")
                }
            }
        }
    }
}


@Preview
@Composable
private fun NewQuestionDialogPreview() {
    MaterialTheme {
        NewQuestionDialogContent(
            onDismissRequest = {},
            uiState = NewQuestionUiState(
                title = "What is your favorite color?",
                options = listOf("Red", "Blue", "Green", "Yellow"),
                correctOptionIndex = 1
            ),
            onSave = {},
            onNewOptionTextChange = {},
            onTitleChange = {},
            onAddNewOption = {},
            onChangeCorrectOptionIndex = {},
            onRemoveOption = {}
        )
    }
}