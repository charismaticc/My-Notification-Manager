package com.sharipov.mynotificationmanager.ui.allnotifications.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyFloatingActionButton(
    onFiltersClick: () -> Unit,
) {
    BoxWithConstraints {
        val fabSize = 56.dp
        val fabMargin = 8.dp
        val maxWidth = maxWidth - fabMargin * 2
        val maxHeight = maxHeight - fabMargin * 2

        Box(
            Modifier
                .padding(bottom = fabMargin, end = fabMargin)
                .widthIn(max = maxWidth)
                .heightIn(max = maxHeight)
                .clip(RoundedCornerShape(percent = 15))
        ) {
            IconButton(
                onClick = {
                    onFiltersClick()
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .size(fabSize),
                ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    tint = Color.White,
                    contentDescription = "filter",
                )
            }
        }
    }
}

