package com.sharipov.mynotificationmanager.ui.settings


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel

@Composable
fun SettingsScreen(homeViewModel: HomeViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn{
            items(3){
                Text("Text")
            }
        }
    }
}