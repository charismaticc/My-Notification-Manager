package com.sharipov.mynotificationmanager.ui.settings.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.data.ThemePreferences
import com.sharipov.mynotificationmanager.ui.theme.AppThemes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selectThemeDialog(
    onThemeModeSelected: (String) -> Unit,
    onThemeStyleSelected: (String) -> Unit,
    onDismiss: () -> Unit
): Boolean {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { openDialog.value = false }
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = MaterialTheme.shapes.large
            ) {
                SelectThemeContent(onThemeModeSelected, onThemeStyleSelected, onDismiss)
            }
        }
    }
    return openDialog.value
}

@Composable
fun SelectThemeContent(
    onThemeSelected: (String) -> Unit,
    onThemeStyleSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val themes = listOf(
        stringResource(id = R.string.dark_theme),
        stringResource(id = R.string.light_theme),
        stringResource(id = R.string.system_theme)
    )

    var selectedThemeMode by remember { mutableStateOf(ThemePreferences.getThemeMode(context)) }
    var selectedTheme by remember { mutableStateOf(ThemePreferences.getTheme(context)) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.select_theme),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Themes(themes, selectedThemeMode) { mode ->
            selectedThemeMode = mode
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().weight(1f).padding(8.dp)
        ) {
            item {
                Card(modifier = Modifier.fillParentMaxSize()) {
                    selectedTheme?.let {
                        PrimaryColor(it) { theme ->
                            selectedTheme = theme
                        }
                    }
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 16.dp, bottom = 32.dp),
            onClick = {
                onDismiss()
                onThemeSelected(selectedThemeMode)
                selectedTheme?.let { onThemeStyleSelected(it) }
            }
        ) {
            Text(stringResource(id = R.string.select), color = Color.White)
        }
    }
}

@Composable
fun Themes(themes: List<String>, selectedThemeMode: String, onThemeSelected: (String) -> Unit) {

    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column {
            themes.forEach { themeName ->
                val themeCode = getThemeModeCode(themes, themeName)
                val isSelected = (themeCode == selectedThemeMode)
                ThemeItem(themeName, themeCode, isSelected, onThemeSelected)
            }
        }
    }
}

fun getThemeModeCode(themes: List<String>, themeMode: String): String {
    return when (themeMode) {
        themes[0] -> "dark_theme"
        themes[1] -> "light_theme"
        themes[2] -> "system_theme"
        else -> "system_theme"
    }
}

@Composable
fun ThemeItem(
    themeMode: String,
    themeCode: String,
    isSelected: Boolean,
    onThemeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onThemeSelected(themeCode) }
            .padding(16.dp)
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(text = themeMode, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun PrimaryColor(selectedTheme: String, onThemeSelected: (String) -> Unit) {
    val themes = AppThemes.getThemesList()

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "System",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Switch(
                    checked = (selectedTheme == "System"),
                    onCheckedChange = { onThemeSelected("System")}
                )
            }
        }

        themes.forEach { theme ->
            item {
                ColorRow(
                    themeName = theme.name,
                    primary = theme.primary,
                    background = theme.background,
                    isSelected = (selectedTheme == theme.name),
                    onThemeSelected = { onThemeSelected(theme.name) }
                )
            }
        }
    }

}

@Composable
fun ColorRow(
    themeName: String,
    primary: Color,
    background: Color,
    isSelected: Boolean,
    onThemeSelected: (String) -> Unit
) {

    val boxModifier = Modifier
        .size(64.dp)
        .padding(8.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onThemeSelected(themeName)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null, modifier = Modifier.padding(start = 16.dp))
        Text(text = themeName, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
            .weight(1f))
        Box(modifier = boxModifier.background(primary, shape = CircleShape))
        Box(modifier = boxModifier.background(background, shape = CircleShape))
        Spacer(modifier = Modifier.padding(end = 8.dp))
    }
}
