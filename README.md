# Google Gemini AI ChatBot

This is an Android application that integrates with the Google Gemini AI to provide a chatbot interface. The chatbot can process user questions and images to generate responses.

## The app may not disply the repsonse if the api is not supported in the location with a proper billing address.

## Features

- Accepts user text input.
- Allows users to upload images.
- Generates responses based on the provided text and images.
- Displays loading, success, and error states.

## Getting Started

### Prerequisites

- Android Studio
- Android SDK
- Google Gemini AI API Key

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/gemini-ai-chatbot.git
    cd gemini-ai-chatbot
    ```

2. **Open the project in Android Studio:**

    ```bash
    android-studio gemini-ai-chatbot
    ```

3. **Add your API key:**

   Replace `YOUR_API_KEY_HERE` with your actual API key in the `local.properties` file:

    ```properties
    API_KEY=YOUR_API_KEY_HERE
    ```

4. **Build and run the project:**

    Click on `Run` in Android Studio or use the following command:

    ```bash
    ./gradlew assembleDebug
    ```

## Usage

1. **Enter your query in the text field.**
2. **Upload images by clicking on the add button.**
3. **Click on the send button to generate a response.**
4. **View the generated response or error message.**

## Project Structure

- `MainActivity.kt`: The main entry point of the application.
- `HomeViewModel.kt`: Contains the logic for interacting with the Google Gemini AI.
- `AppContent.kt`: Composable function that builds the UI.
- `HomeScreen.kt`: Composable function that renders the home screen UI.
- `HomeUIState.kt`: Defines the different UI states (Initial, Loading, Success, Error).

## Dependencies

- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for building the UI.
- [Coil](https://coil-kt.github.io/coil/) for image loading.
- [Google Gemini AI SDK](https://github.com/google/gemini-ai) for AI integration.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [Google Gemini AI](https://ai.google/) for providing the generative model.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the UI toolkit.
- [Coil](https://coil-kt.github.io/coil/) for image loading.

## Contributing

1. Fork the repository.
2. Create a new branch.
3. Make your changes.
4. Submit a pull request.

## Contact

If you have any questions or feedback, please contact [your-email@example.com].
