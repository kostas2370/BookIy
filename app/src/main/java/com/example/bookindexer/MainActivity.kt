package com.example.bookindexer
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookindexer.data.SessionManager
import com.example.bookindexer.screens.LoginScreen
import com.example.bookindexer.screens.RegisterScreen
import com.example.bookindexer.screens.StartScreen


class MainActivity : ComponentActivity() {
    private lateinit var sessionManager: SessionManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)

        setContent {

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "StartScreen") {
                composable("LoginScreen") { LoginScreen(navController) { access ->
                    sessionManager.saveAuthToken(
                        access
                    )
                    val navigate = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(navigate)
                    }
                }
                composable("RegisterScreen") { RegisterScreen(navController) }
                composable("StartScreen") { StartScreen(navController) }
            }


        }
}









}
