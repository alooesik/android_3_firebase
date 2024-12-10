package com.example.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebase.ui.theme.FirebaseTheme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTheme {
                val navController = rememberNavController()
                val databaseReference = FirebaseDatabase.getInstance().getReference("StudentInfo")

                NavHost(
                    navController = navController,
                    startDestination = Routes.screenIntroduction
                ) {
                    composable(Routes.screenIntroduction) {
                        ScreenIntroduction(navController)
                    }
                    composable(Routes.screenFirebase) {
                        ScreenFirebase(
                            context = this@MainActivity,
                            databaseReference = databaseReference,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}