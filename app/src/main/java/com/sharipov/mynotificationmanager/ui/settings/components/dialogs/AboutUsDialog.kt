package com.sharipov.mynotificationmanager.ui.settings.components.dialogs

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sharipov.mynotificationmanager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun aboutUsDialog(): Boolean {
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
                AboutUsDialogContent()
            }
        }
    }
    return openDialog.value
}

@Composable
fun AboutUsDialogContent() {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_app),
                contentDescription = "app_icon",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(id = R.string.developer),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${stringResource(R.string.app_version_text)} " +
                            stringResource(R.string.app_version),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        Button(
            onClick = {
                val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://play.google.com/store/apps/details?id=com.sharipov.mynotificationmanager")
                }
                launcher.launch(telegramIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.rate_the_app),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.padding(24.dp))
    }
}