package com.example.bookindexer.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookindexer.api.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(token: String, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var passwordVer by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Settings Page:",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username ") },
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email ") },
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            modifier = Modifier,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordVer,
            onValueChange = { passwordVer = it },
            label = { Text("Password verification") },
            modifier = Modifier,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(46.dp))

        OutlinedTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text("Old Password") },
            modifier = Modifier,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {
                if (newPassword != "") {
                    if (newPassword != passwordVer) {
                        Toast.makeText(context, "Wrong password Verification", Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }


                }
                if (oldPassword == "") {
                    Toast.makeText(context, "You need to add the old password", Toast.LENGTH_LONG)
                        .show()
                    return@Button
                }

                coroutineScope.launch {
                    try {

                            RetrofitInstance.api.updateProfile(
                                token, username,
                                email,
                                oldPassword,
                                newPassword
                            )

                        navController.navigate("Home")


                    } catch (e: Exception) {
                        if (e.localizedMessage.equals("HTTP 400 Bad Request")) {
                            Toast.makeText(
                                context,
                                "Wrong old password",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }


            },
            modifier = Modifier
                .height(60.dp)
                .width(250.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )

        ) {
            Text("Update")
        }

    }
}