package com.sharipov.mynotificationmanager.ui.drawer.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MyNavigationDrawerItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter
) {
    NavigationDrawerItem(
        label = { Text(text = label, style = MaterialTheme.typography.bodyLarge) },
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = icon,
                contentDescription = null
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}