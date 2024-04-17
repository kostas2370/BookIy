package com.example.bookindexer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookindexer.Model.BookReviewViewModel
import com.example.bookindexer.Model.BookViewModel
import com.example.bookindexer.api.RetrofitInstance
import com.example.bookindexer.data.OwnReview
import kotlinx.coroutines.launch

@Composable
fun bookReview(id: String?, token: String, navController: NavController,
               bookViewModel : BookViewModel, reviewViewModel: BookReviewViewModel, bookown : OwnReview
){

    var review by remember { mutableStateOf(bookown.review) }
    val coroutineScope = rememberCoroutineScope()
    val ratingState = remember { mutableIntStateOf(bookown.score) }
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        UserRatingBar(size = 50.dp, ratingState = ratingState, modifier = Modifier.padding(top=270.dp, start = 78.dp))

    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){

        Spacer(modifier = Modifier.height(53.dp))

        Text(text = "Review :",  fontWeight = FontWeight.Bold ,fontSize = 30.sp,modifier= Modifier.padding(top = 300.dp))
        OutlinedTextField(
            value = review,
            onValueChange = { review = it },
            label = { Text("Your review") },
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.height(42.dp))

        OutlinedButton(
            onClick = {
                coroutineScope.launch {

                    RetrofitInstance.api
                        .postReview(token, id.toString(), review, ratingState.value.toString())



                    bookViewModel.loadBook()
                    reviewViewModel.loadReview()
                    navController.navigate("Info")


                }},
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 20.dp, end = 20.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black)
        ) {
            Text("Review")
        }
    }

}