package com.sharipov.mynotificationmanager.ui.conversations.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sharipov.mynotificationmanager.R

@Composable
fun DeleteAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.delete_chat) + "?",
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}