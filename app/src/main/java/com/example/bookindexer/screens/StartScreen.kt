package com.example.bookindexer.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookindexer.R

@Composable
fun StartScreen(navController: NavController){
    val image: Painter = painterResource(id = R.drawable.booklylogo)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(painter = image,contentDescription = "", Modifier.size(340.dp))
        Text(text = "Welcome to Bookly ",  fontWeight = FontWeight.Bold ,fontSize = 30.sp)
        Spacer(modifier = Modifier.height(19.dp))

        Text(text = "We are here to help you manage all your favourite books !" ,fontSize = 24.sp)
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                navController.navigate("LoginScreen")
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White)
        ) {
            Text("Log in")
        }
        Spacer(modifier = Modifier.height(48.dp))
        OutlinedButton(
            onClick = {
                navController.navigate("RegisterScreen")
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black)
        ) {
            Text("Create Account")
        }
    }}