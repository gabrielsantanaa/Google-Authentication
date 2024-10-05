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
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gabrielsantana.letsvote.screens.poll.QuestionUiModel

@Composable
fun NewQuestionDialogScreen(
    onNavigateBack: () -> Unit,
) {
    NewQuestionDialogContent(
        onDismissRequest = onNavigateBack,
        question = QuestionUiModel(
            "What is your favorite color?",
            listOf("Red", "Blue", "Green", "Yellow"),
            1
        )
    )
}

@Composable
fun NewQuestionDialogContent(
    question: QuestionUiModel,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
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
                    value = "",
                    onValueChange = {},
                    label = { Text("Question title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(Modifier.selectableGroup()) {
                    question.options.forEachIndexed { index, text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (index == question.correctOptionIndex),
                                    onClick = { },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (index == question.correctOptionIndex),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            Spacer(Modifier.weight(1F))
                            IconButton(onClick = {}) {
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
                        value = "",
                        onValueChange = {},
                        label = { Text("New option") },
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                    )
                    FilledIconButton(onClick = {}) {
                        Icon(Icons.Default.Add, contentDescription = "Add option")
                    }
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
            question = QuestionUiModel(
                "What is your favorite color?",
                listOf("Red", "Blue", "Green", "Yellow"),
                1
            )
        )
    }
}