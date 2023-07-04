package com.sharipov.mynotificationmanager.ui.notificationmanagement.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharipov.mynotificationmanager.R

@Composable
fun ToggleDisplayMode(whichListIsDisplayed: MutableState<Boolean>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (whichListIsDisplayed.value){
                        if(isSystemInDarkTheme())
                            Color.DarkGray
                        else Color(android.graphics.Color.parseColor("#DCDCDC"))
                    }
                    else MaterialTheme.colorScheme.background
                )
                .clickable {
                    whichListIsDisplayed.value = true
                },
        ) {
            Text(
                text = stringResource(id = R.string.save_notifications),
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (!whichListIsDisplayed.value){
                        if(isSystemInDarkTheme())
                            Color.DarkGray
                        else Color(android.graphics.Color.parseColor("#DCDCDC"))
                    }
                    else MaterialTheme.colorScheme.background
                )
                .clickable {
                    whichListIsDisplayed.value = false
                },
        ) {
            Text(
                text = stringResource(id = R.string.block_notifications),
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}