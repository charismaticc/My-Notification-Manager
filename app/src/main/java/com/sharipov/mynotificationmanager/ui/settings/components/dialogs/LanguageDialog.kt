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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.data.PreferencesManager

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
                    Text(stringResource(id = R.string.select), color = Color.White,)
                }
            }
        }
    }
}