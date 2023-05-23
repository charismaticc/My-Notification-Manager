package com.sharipov.mynotificationmanager.ui.settings.components

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.model.AppSettingsEntity
import com.sharipov.mynotificationmanager.model.ExcludedAppEntity
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun autoRemoveDialog(
    settingsViewModel: SettingsViewModel,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
): Boolean {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }
    val timeOptions = listOf("Newer", "1 hour", "1 day", "1 week", "2 weeks", "1 month")

    var selectedTime = "Never"
    var selectedTimeLong = 0L

    runBlocking {
        val appSettings = settingsViewModel.getAppSettings()
        if (appSettings != null) {
            selectedTime = appSettings.autoDeleteTimeoutString
            selectedTimeLong = appSettings.autoDeleteTimeoutLong
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
                    text = "Auto-delete time",
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
                            "Newer" -> 0L
                            "1 hour" -> 1 * 60 * 60 * 1000L
                            "1 day" -> 24 * 60 * 60 * 1000L
                            "1 week" -> 7 * 24 * 60 * 60 * 1000L
                            "2 weeks" -> 14 * 24 * 60 * 60 * 1000L
                            "1 month" -> 30 * 24 * 60 * 60 * 1000L
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
                    Text("Select")
                }
            }
        }
    }
    return openDialog.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selectAppsDialog(
    settingsViewModel: SettingsViewModel
): Boolean {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val apps = if (searchQuery.isNotBlank()) {
        isSearching = true
        settingsViewModel.searchApplication(searchQuery).collectAsState(emptyList()).value
    } else {
        settingsViewModel.getAllExcludedApps().collectAsState(emptyList()).value
    }

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 32.dp, bottom = 32.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                if (apps.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        items(apps) { app ->
                            val icon = context.packageManager.getApplicationIcon(app.packageName)
                            AppListItem(
                                settingsViewModel = settingsViewModel,
                                icon = icon,
                                appEntity = app
                            )
                        }
                    }
                }
            }
        }
    }
    return openDialog.value
}

@Composable
fun AppListItem(
    settingsViewModel: SettingsViewModel,
    icon: Drawable,
    appEntity: ExcludedAppEntity
) {
    val isChecked = remember { mutableStateOf(!appEntity.isExcluded) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberDrawablePainter(drawable = icon),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = appEntity.appName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { newValue ->
                isChecked.value = newValue
                settingsViewModel.viewModelScope.launch {
                    val newAppEntity = appEntity.copy(isExcluded = newValue)
                    withContext(Dispatchers.IO) {
                        settingsViewModel.updateExcludedApp(newAppEntity)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun privatePolicyDialog(): Boolean {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(id = R.string.private_policy),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
    return openDialog.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun feedbackDialog(): Boolean {
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
                Text(text = "feedbackDialog")
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
                Text(text = "aboutUsDialog")
            }
        }
    }
    return openDialog.value
}