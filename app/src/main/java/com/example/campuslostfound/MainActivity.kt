package com.example.campuslostfound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.campuslostfound.ui.navigation.CampusLostFoundApp
import com.example.campuslostfound.theme.CampusLostFoundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusLostFoundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CampusLostFoundApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
