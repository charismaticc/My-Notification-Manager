package com.sharipov.mynotificationmanager.ui.conversations.component

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.accompanist.drawablepainter.rememberDrawablePainter


@Composable
fun ApplicationItem(packageName: String, modifier: Modifier) {

    val context = LocalContext.current

    // get app icon
    val appIconDrawable = context.packageManager.getApplicationIcon(packageName)

    // get app name
    val appInfo = context.packageManager.getApplicationInfo(packageName, 0)
    val appName = context.packageManager.getApplicationLabel(appInfo).toString()


    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(8.dp, 16.dp, 16.dp, 16.dp)) {
                Image(
                    painter = rememberDrawablePainter(appIconDrawable),
                    contentDescription = "App icon",
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = appName,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1
                )
            }
        }
    }
}