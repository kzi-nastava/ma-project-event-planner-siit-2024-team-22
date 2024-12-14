package com.example.eventplannerteam22.presentation.screens

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CreateService(navController: NavController) {
    var name by remember {
        mutableStateOf("")
    }

    var name1 by remember {
        mutableStateOf("")
    }

    var name2 by remember {
        mutableStateOf("")
    }

    var name3 by remember {
        mutableStateOf("")
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            // Convert URI to Bitmap
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    Column {
        Text("Create service")
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = {Text("Name")})

        TextField(
            value = name1,
            onValueChange = {
                name1 = it
            },
            placeholder = {Text("Description")}
        )

        TextField(
            value = name2,
            onValueChange = {
                name2 = it
            },
            placeholder = {Text("Specifics")})

        TextField(
            value = name3,
            onValueChange = {
                name3 = it
            },
            placeholder = {Text("Price")}
        )

        TextField(
            value = name3,
            onValueChange = {
                name3 = it
            },
            placeholder = {Text("Price")}
        )

        TextField(
            value = name3,
            onValueChange = {
                name3 = it
            },
            placeholder = {Text("Discount")}
        )

        Column(
        ) {
            Text(
                text = "Select an Image to Upload",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Pick Image")
            }

            Spacer(modifier = Modifier.height(16.dp))

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
            }
        }
        Button(
            onClick = {

            }
        ) {
            Text("Submit")
        }
    }
}