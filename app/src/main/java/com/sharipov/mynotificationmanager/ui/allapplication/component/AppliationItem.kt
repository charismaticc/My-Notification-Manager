package com.sharipov.mynotificationmanager.ui.allapplication.component

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.sharipov.mynotificationmanager.R


@Composable
fun ApplicationItem(
    packageName: String,
    appName: String,
    notificationCount: Int,
    modifier: Modifier
) {

    val context = LocalContext.current

    // get app icon
    val appIconDrawable: Drawable? = try {
        context.packageManager.getApplicationIcon(packageName)
    } catch (e: PackageManager.NameNotFoundException) {
        ContextCompat.getDrawable(context, R.drawable.ic_android)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
    ){
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(8.dp, 16.dp, 16.dp, 16.dp)) {

                Image(
                    painter = rememberDrawablePainter(appIconDrawable),
                    contentDescription = "App icon",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = appName,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1
                )

                Text(
                    text = notificationCount.toString(),
                    textAlign = TextAlign.End,
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