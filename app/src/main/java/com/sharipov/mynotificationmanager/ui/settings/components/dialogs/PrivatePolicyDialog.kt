package com.sharipov.mynotificationmanager.ui.settings.components.dialogs

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sharipov.mynotificationmanager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun privatePolicyDialog(): Boolean {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        ModalBottomSheet(
            onDismissRequest = {
                openDialog.value = false
            },
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                PrivatePolicyDialogContext()
            }
        }
    }
    return openDialog.value
}

@Composable
fun PrivatePolicyDialogContext() {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.private_policy_text),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = {
                val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://sites.google.com/view/my-notification-manager-privac")
                }
                launcher.launch(telegramIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.private_policy),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}