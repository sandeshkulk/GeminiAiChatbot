package com.example.geminiaichatbot.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.geminiaichatbot.HomeUIState
import com.example.geminiaichatbot.HomeViewModel
import com.example.geminiaichatbot.UriSaver

@Composable
fun AppContent(viewModel: HomeViewModel= androidx.lifecycle.viewmodel.compose.viewModel()){
    val appUiState=viewModel.uiState.collectAsState()
    HomeScreen(uiSate = appUiState.value){ inputText,selectedItems->
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiSate: HomeUIState = HomeUIState.Loading,
    onSendClicked: (String, List<Uri>) -> Unit
) {
    var userQuery by rememberSaveable { mutableStateOf("") }
    val imageUris = rememberSaveable(saver = UriSaver()) { mutableStateListOf<Uri>() }

    val pickMediaLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { imageUri ->
            imageUri?.let {
                imageUris.add(it)
            }
        }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Google Gemini AI ChatBot") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Column(verticalArrangement = Arrangement.Bottom) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                  Log.d("","")},
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Upload Image")
                    }
                    OutlinedTextField(
                        value = userQuery,
                        onValueChange = { userQuery = it },
                        label = { Text(text = "User Input")},
                        placeholder = { Text(text = "Upload the image and Start your Query Search") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    IconButton(
                        onClick = {
                            if(userQuery.isNotBlank()){
                                onSendClicked
                            }
                            Log.d("Send Button", "Send Clicked")
                            onSendClicked(userQuery, imageUris)
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send Button")
                    }
                }
                AnimatedVisibility(visible = imageUris.size>0) {
                    LazyRow(modifier = Modifier.padding(8.dp)) {
                        items(imageUris){ imageUri->
                            Column(verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                AsyncImage(model = imageUri, contentDescription = "",
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .requiredSize(50.dp))
                                TextButton(onClick = { imageUris.remove(imageUri)}) {
                                    Text(text = "Discard")
                                }
                            }
                        }

                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (uiSate) {
                // Initial State
                is HomeUIState.Intial -> { /* Initial UI */ }
                // Loading
                is HomeUIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                // Success
                is HomeUIState.Success -> {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(text = uiSate.outputText)
                    }
                }
                // Error
                is HomeUIState.Error -> {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(text = uiSate.errorText)
                    }
                }
            }
        }
    }
}


