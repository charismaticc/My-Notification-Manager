@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharipov.mynotificationmanager.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold {
        LazyColumn {
            items(3) {
                Text("Text")
            }
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}