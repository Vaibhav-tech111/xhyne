package com.xhyne

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.xhyne.ui.theme.XhyneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XhyneTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "chat") {
                    composable("chat") {
                        ChatScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun ChatScreen() {
    // This will be implemented in a later phase.
}