package com.sharipov.mynotificationmanager.ui.notificationmanagement

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.ui.bottombarcomponent.BottomBar
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.ui.notificationmanagement.component.AllApplication
import com.sharipov.mynotificationmanager.ui.notificationmanagement.component.AppListItem
import com.sharipov.mynotificationmanager.ui.notificationmanagement.component.ToggleDisplayMode
import com.sharipov.mynotificationmanager.ui.topbarscomponent.SearchTopBarContent
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationFilterScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    val whichListIsDisplayed = remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var searchVisible by remember { mutableStateOf(false) }

    val apps = if (searchQuery.isNotBlank()) {
        settingsViewModel.searchApplication(searchQuery).collectAsState(emptyList()).value
    } else {
        settingsViewModel.getAllExcludedApps().collectAsState(emptyList()).value
    }

    TransparentSystemBars()

    Scaffold(
        topBar = {
            SearchTopBarContent(
                title = stringResource(id = R.string.notification_management),
                onSearchClick = { searchVisible = !searchVisible },
                searchVisible = searchVisible,
                searchText = searchQuery,
                onSearchTextChange = { searchQuery = it }
            )
        }
    ) {
        Log.d("TAG", settingsViewModel.getAllExcludedApps().toString())

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            AnimatedVisibility(
                visible = searchVisible
            ) {
                Spacer(modifier = Modifier.padding(36.dp))
            }

            ToggleDisplayMode(whichListIsDisplayed)

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {
                if (!searchVisible) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if(!whichListIsDisplayed.value) stringResource(id = R.string.block_notifications_rules)
                                    else stringResource(id = R.string.save_notifications_rules),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    }
                }
                item {
                    AllApplication(settingsViewModel, whichListIsDisplayed.value)
                }
                items(apps) { app ->
                    val icon = context.packageManager.getApplicationIcon(app.packageName)
                    AppListItem(
                        settingsViewModel = settingsViewModel,
                        icon = icon,
                        whichListIsDisplayed = whichListIsDisplayed.value,
                        appEntity = app
                    )
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                }
                item { Spacer(modifier = Modifier.height(60.dp)) }
            }
        }
    }
}


