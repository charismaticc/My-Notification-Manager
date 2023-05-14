package com.sharipov.mynotificationmanager.ui.topbarscomponent

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBarContent(
    title: String,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    searchVisible: Boolean,
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description"
                )
            }
        },
    )

    if (searchVisible) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearchClick()
                }
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContent(
    title: String,
    icon: ImageVector,
    onNavigationClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(
                    imageVector = icon,
                    contentDescription = "icon"
                )
            }
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatTopBarContent(
    userName: String,
    packageName: String,
    searchVisible: Boolean,
    searchText: String,
    navController: NavController,
    homeViewModel: HomeViewModel,
    onSearchVisibleChange: (Boolean) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = userName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.basicMarquee()
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigateUp()
                }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow back"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {  expanded = true
                }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More vert"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .offset {
                        IntOffset(0, 0)
                    }
            ) {
                DropdownMenuItem(
                    text = {
                        Row {
                            Icon(Icons.Default.Search, "Search in chat")
                            Spacer(Modifier.width(8.dp))
                            Text("Find")
                        }
                    },
                    onClick = {
                        onSearchVisibleChange(!searchVisible)
                        expanded = false
                    },

                    )

                Divider()

                DropdownMenuItem(
                    text = {
                        Row {
                            Icon(Icons.Default.Delete, "Delete chat")
                            Spacer(Modifier.width(8.dp))
                            Text("Delete chat")
                        }
                    },
                    onClick = {
                        homeViewModel.deleteNotificationsForUser(userName, packageName)
                        navController.navigateUp()
                        expanded = false
                    }
                )
            }
        }
    )

    if (searchVisible) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearchClick()
                }
            ),
        )
    }
}