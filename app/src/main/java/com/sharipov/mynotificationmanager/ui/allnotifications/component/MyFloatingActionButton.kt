package com.sharipov.mynotificationmanager.ui.allnotifications.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        ) {
            FloatingActionButton(
                onClick = {
                    onFiltersClick()
                },
                modifier = Modifier
                    .size(fabSize)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "filter"
                )
            }
        }
    }
}

