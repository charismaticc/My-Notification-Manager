package com.sharipov.mynotificationmanager.ui.settings.components

import android.graphics.drawable.Drawable
import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
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
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.sharipov.mynotificationmanager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun autoRemoveDialog(
    selectedTime: String,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
): Boolean {

    val openDialog = remember { mutableStateOf(true) }
    val timeOptions = listOf("Newer", "1 hour", "1 day", "1 week", "2 weeks", "1 month")
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
                    modifier = Modifier.align(Alignment.CenterHorizontally) ,
                    text = "Auto-delete time",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                timeOptions.forEach { text ->
                    Row(Modifier.selectable(
                        selected = (text == selectedOption),
                        onClick = { selectedOption = text }
                    ).fillMaxWidth().padding(16.dp)) {
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
fun selectAppsDialog(): Boolean {
    val openDialog = remember { mutableStateOf(true) }

    val packageManager = LocalContext.current.packageManager
    val apps = packageManager.getInstalledPackages(0)


    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(top = 32.dp, bottom = 32.dp),
            shape = MaterialTheme.shapes.large
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
            ) {
                items(apps) { app ->
                    val icon = packageManager.getApplicationIcon(app.applicationInfo)
                    val appName = app.applicationInfo.loadLabel(packageManager).toString()

                    AppListItem(icon = icon, appName = appName)
                }
            }
        }
    }
    return openDialog.value
}

@Composable
fun AppListItem(icon: Drawable, appName: String) {
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
            text = appName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Checkbox(checked = false, onCheckedChange = { /* todo */ })
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