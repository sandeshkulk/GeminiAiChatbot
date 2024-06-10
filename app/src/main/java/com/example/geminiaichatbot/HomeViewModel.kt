package com.example.geminiaichatbot

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.geminiaichatbot.BuildConfig
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState.Intial)
    val uiState = _uiState.asStateFlow()

    private var generativeModel: GenerativeModel

    init {
        val config = generationConfig {
            temperature = 0.7f
        }
        generativeModel = GenerativeModel(
            modelName = "gemini-1.5-pro-latest",
            apiKey = BuildConfig.apikey,
            generationConfig = config
        )
    }

    fun questioning(userInput: String, selectedImages: List<Bitmap>) {
        _uiState.value = HomeUIState.Loading
        val prompt = "Take a look at the images and then answer the question: $userInput"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = content {
                    for (bitmap in selectedImages) {
                        image(bitmap)
                    }
                    text(prompt)
                }
                var output = ""
                generativeModel.generateContentStream(content).collect {
                    output += it.text
                    _uiState.value = HomeUIState.Success(output)
                }
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.localizedMessage ?: "Error Generating Content")
            }
        }
    }
}


sealed interface HomeUIState{
    object Intial:HomeUIState
    object Loading:HomeUIState
    data class Success(val outputText:String):HomeUIState
    data class Error(val errorText:String):HomeUIState
}