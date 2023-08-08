package com.sharipov.mynotificationmanager.ui.settings.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.data.PreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selectThemeDialog(
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
): Boolean {
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
            SelectThemeDialogContent(onThemeSelected, onDismiss)
        }
    }
    return openDialog.value
}

@Composable
fun SelectThemeDialogContent(
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val darkTheme = stringResource(id = R.string.dark_theme)
    val lightTheme = stringResource(id = R.string.light_theme)
    val systemTheme = stringResource(id = R.string.system_theme)
    val themes = listOf(darkTheme, lightTheme, systemTheme)

    val currentTheme = when(PreferencesManager.getThemeStyle(context)) {
        "dark_theme" -> darkTheme
        "light_theme" -> lightTheme
        "system_theme" -> systemTheme
        else -> systemTheme
    }

    var selectedTheme by remember { mutableStateOf(currentTheme) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.select_theme),
            style = MaterialTheme.typography.titleLarge
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
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onDismiss.invoke()
                val theme = when (selectedTheme) {
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