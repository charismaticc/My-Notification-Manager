package com.sharipov.mynotificationmanager.ui.settings.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharipov.mynotificationmanager.data.PreferencesManager
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.model.AppSettingsEntity
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun autoRemoveDialog(
    settingsViewModel: SettingsViewModel,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
): Boolean {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }
    val timeOptions = listOf(
        stringResource(R.string.never),
        stringResource(R.string.one_hour),
        stringResource(R.string.one_day),
        stringResource(R.string.one_week),
        stringResource(R.string.two_weeks),
        stringResource(R.string.one_month)
    )

    var selectedTime = stringResource(R.string.never)
    var selectedTimeLong = 0L

    runBlocking {
        val appSettings = settingsViewModel.getAppSettings()
        if (appSettings != null) {
            selectedTime = appSettings.autoDeleteTimeoutString
            selectedTimeLong = appSettings.autoDeleteTimeoutLong
            if (selectedTime !in timeOptions) {
                selectedTime = when (selectedTimeLong) {
                    0L -> context.getString(R.string.never)
                    3600000L -> context.getString(R.string.one_hour)
                    86400000L -> context.getString(R.string.one_day)
                    604800000L -> context.getString(R.string.one_week)
                    1209600000L -> context.getString(R.string.two_weeks)
                    2592000000L -> context.getString(R.string.one_month)
                    else -> context.getString(R.string.never)
                }
            }
        } else {
            settingsViewModel.saveAppSettings(AppSettingsEntity(0, selectedTimeLong, selectedTime))
        }
    }

    var selectedOption by remember { mutableStateOf(selectedTime) }

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.auto_delete_time),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                timeOptions.forEach { text ->
                    Row(Modifier
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { selectedOption = text }
                        )
                        .fillMaxWidth()
                        .padding(16.dp)) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null so that the processing is only on Row
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selectedTimeLong = when (selectedOption) {
                            context.getString(R.string.never) -> 0L
                            context.getString(R.string.one_hour) -> 3600000L
                            context.getString(R.string.one_day) -> 86400000L
                            context.getString(R.string.one_week) -> 604800000L
                            context.getString(R.string.two_weeks) -> 1209600000L
                            context.getString(R.string.one_month) -> 2592000000L
                            else -> 0L
                        }

                        runBlocking {
                            settingsViewModel.updateSettings(AppSettingsEntity(0, selectedTimeLong, selectedOption))
                        }

                        Toast.makeText(context, selectedOption, Toast.LENGTH_LONG).show()

                        onTimeSelected(selectedOption)
                        onDismiss()
                    }
                ) {
                    Text(stringResource(id = R.string.select))
                }
            }
        }
    }
    return openDialog.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun privatePolicyDialog(): Boolean {
    val openDialog = remember { mutableStateOf(true) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.private_policy),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
    return openDialog.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun feedbackDialog(): Boolean {
    val openDialog = remember { mutableStateOf(true) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
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

                Button(
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("akbar20sharipov01@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "My Notification Manager")
                        }
                        launcher.launch(emailIntent)
                    },
                    modifier = Modifier.fillMaxWidth()
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
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    onClick = {
                        val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://t.me/MyNotificationManager")
                        }
                        launcher.launch(telegramIntent)
                    },
                    modifier = Modifier.fillMaxWidth()
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
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
    return openDialog.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun aboutUsDialog(): Boolean {
    val openDialog = remember { mutableStateOf(true) }
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(id = R.string.app_name))
                Text(text = stringResource(id = R.string.developer))
                Text(text = "${stringResource(R.string.app_version_text)} ${stringResource(R.string.app_version)}")

            }
        }
    }
    return openDialog.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDialog(onLanguageSelected: (String) -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val languages = listOf("English", "Русский", "Тоҷикӣ", "Українська")
    var selectedLanguage by remember { mutableStateOf(PreferencesManager.getSelectedLanguage(context) ?: "English") }

    AlertDialog(
        onDismissRequest = {
            onDismiss.invoke()
        }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.select_language),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                languages.forEach { text ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (text == selectedLanguage),
                                onClick = { selectedLanguage = text }
                            )
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(
                            selected = (text == selectedLanguage),
                            onClick = null // null so that the processing is only on Row
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss.invoke()
                        onLanguageSelected.invoke(selectedLanguage)
                    }
                ) {
                    Text(stringResource(id = R.string.select))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectThemeDialog(
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val themes = listOf(
        stringResource(id = R.string.dark_theme),
        stringResource(id = R.string.light_theme),
        stringResource(id = R.string.system_theme)
    )

    val currentTheme = when(PreferencesManager.getThemeStyle(context)) {
        "dark_theme" -> stringResource(id = R.string.dark_theme)
        "light_theme" -> stringResource(id = R.string.light_theme)
        "system_theme" -> stringResource(id = R.string.system_theme)
        else -> stringResource(id = R.string.system_theme)
    }

    var selectedTheme by remember { mutableStateOf(currentTheme) }

    AlertDialog(
        onDismissRequest = {
            onDismiss.invoke()
        }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.select_theme),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                themes.forEach { text ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (text == selectedTheme),
                                onClick = { selectedTheme = text }
                            )
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(
                            selected = (text == selectedTheme),
                            onClick = null // null so that the processing is only on Row
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                val darkTheme = stringResource(id = R.string.dark_theme)
                val lightTheme = stringResource(id = R.string.light_theme)
                val systemTheme = stringResource(id = R.string.system_theme)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss.invoke()
                        val theme = when(selectedTheme) {
                            darkTheme -> "dark_theme"
                            lightTheme -> "light_theme"
                            systemTheme -> "system_theme"
                            else -> "system_theme"
                        }
                        onThemeSelected.invoke(theme)

                    }
                ) {
                    Text(stringResource(id = R.string.select))
                }
            }
        }
    }
}