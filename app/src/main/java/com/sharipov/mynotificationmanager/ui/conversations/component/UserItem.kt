package com.sharipov.mynotificationmanager.ui.conversations.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import com.sharipov.mynotificationmanager.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserItem(
    homeViewModel: HomeViewModel,
    navController: NavController,
    group: String,
    userName: String,
    packageName: String,
    userCount: Int,
    modifier: Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(),
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = {
                    navController.navigate(Screens.Chat.route + "/${userName}/${packageName}/${group}")
                },
                onLongClick = {
                    coroutineScope.launch {
                        showDialog = true
                    }
                }
            )
        ) {
            Row(
                modifier = Modifier.padding(8.dp, 16.dp, 16.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = if(group != "not_group") painterResource(id = R.drawable.ic_groups)
                            else  painterResource(id = R.drawable.ic_person),
                    contentDescription = "User icon",
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = if(group == "not_group") userName else group,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1
                )
            }

            if (showDialog) {
                DeleteAlertDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        homeViewModel.deleteNotificationsForUser(group, userName, packageName)
                        showDialog = false
                        if (userCount <= 0) {
                            navController.navigateUp()
                        }
                    },
                    onCancel = { showDialog = false }
                )
            }
        }
    }
}