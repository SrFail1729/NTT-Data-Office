import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.example.nttdata.App

import com.example.nttdata.data.local.WasmSecurePreferences

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val securePreferences = WasmSecurePreferences()
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        App(securePreferences = securePreferences)
    }
}
