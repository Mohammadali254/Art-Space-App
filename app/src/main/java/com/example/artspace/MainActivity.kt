package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                 {
                    // call app to render on device
                     ArtSpaceApp()
                 }
            }
        }
    }
}


@Composable
// calls logic of the app and arrangement
fun ArtSpaceApp(){
    val images = listOf(
        painterResource(id = R.drawable.androidparty),
        painterResource(id = R.drawable.bg_compose_background),
        painterResource(id = R.drawable.ic_task_completed),
        )

      val titles = listOf(
          stringResource(R.string.android_background),
          stringResource(R.string.android_party),
          stringResource(R.string.android_completed),
      )

     val artistNames = listOf(
         stringResource(R.string.Futa) ,
         stringResource( R.string.Heyo),
         stringResource(R.string.Vika)
     )

// state to track current image state
   var currentIndex by remember { mutableStateOf(0) }




   AppLayout(
       image = images[currentIndex] ,
       title = titles[currentIndex],
       artistName =artistNames[currentIndex],

       onNext = {
           // if the current image is not at the last index/image(image.size -1) then when clicked move to the next image (currentIndex ++)
           if(currentIndex < images.size - 1) currentIndex ++
       },
       // if the current image is not the first image then move to the previous image(currentIndex--)
       onPrevious = {
           if(currentIndex > 0) currentIndex --
       },

       isNextEnabled = when (currentIndex){
           images.size - 1 -> false // disable "Next" on last image
           else -> true
       },
       isPreviousEnabled = when(currentIndex){
           0 -> false // disable "Previous" on first Image
           else -> true
       }

       )
}




@Composable
// App Logic
private fun AppLayout(
    image : Painter,
    title : String,
    artistName : String,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    isPreviousEnabled : Boolean,
    isNextEnabled : Boolean,
    modifier: Modifier = Modifier
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        // Column for the Image composable
        Column {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
                    .clip(CircleShape)

            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Column for the two text composable
        Column {
            Text(text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif)

            Text(text = artistName,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 4.dp),
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

      Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
            //Previous buttons
           Button(onClick = onPrevious,
               enabled = isPreviousEnabled) {
               Text("Previous")
           }

            // Next Button
           Button (onClick = onNext,
                   enabled = isNextEnabled) {
               Text("Next")
           }
        }


    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}