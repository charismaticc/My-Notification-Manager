package com.sharipov.mynotificationmanager.ui.settings.components.dialogs

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharipov.mynotificationmanager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun feedbackDialog(): Boolean {
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
                FeedBackBottomSheetContent()
            }
        }
    }
    return openDialog.value
}

@Composable
fun FeedBackBottomSheetContent() {
    val launcher
    = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.communication_method),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            softWrap = true
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("akbar20sharipov01@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "My Notification Manager")
                    }
                    launcher.launch(emailIntent)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp),
                    painter = painterResource(R.drawable.ic_mail),
                    contentDescription = "Icon",
                )
                Text(
                    text = "Email",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.padding(end = 8.dp))
            Button(
                onClick = {
                    val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://t.me/MyNotificationManager")
                    }
                    launcher.launch(telegramIntent)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp),
                    painter = painterResource(R.drawable.ic_telegram),
                    contentDescription = "Icon",
                )
                Text(
                    text = "Telegram",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))
    }
}