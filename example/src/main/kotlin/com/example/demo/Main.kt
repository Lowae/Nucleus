package com.example.demo

//import io.github.kdroidfilter.nucleus.aot.runtime.AotRuntime
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File
import java.net.URI

private const val AOT_TRAINING_DURATION_MS = 45_000L

private val deepLinkUri = mutableStateOf<URI?>(null)

@Suppress("LongMethod")
fun main(args: Array<String>) {

    // Stop app after 15 seconds during AOT training mode
    // Use -Dnucleus.aot.mode=training to test
//    if (AotRuntime.isTraining()) {
//        println("[AOT] Training mode - will exit in 15 seconds")
//
//        Thread({
//            Thread.sleep(AOT_TRAINING_DURATION_MS)
//            println("[AOT] Time's up, exiting...")
//            exitProcess(0)
//        }, "aot-timer").apply {
//            isDaemon = false
//            start()
//        }
//    }

    application {
        val windowState = rememberWindowState()
        Window(
            state = windowState,
            onCloseRequest = {
                windowState.isMinimized = true
            },
            title = "Nucleus",
        ) {
            MaterialTheme() {
                app()
            }
        }
    }
}

@Composable
fun app() {
    val currentDeepLink by deepLinkUri

    var updateStatus by remember { mutableStateOf("Checking for updates...") }
    var downloadProgress by remember { mutableStateOf(-1.0) }
    var downloadedFile by remember { mutableStateOf<File?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NucleusAtom(atomSize = 200.dp)

                if (currentDeepLink != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Deep Link",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentDeepLink.toString(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

private enum class ThemeMode {
    System,
    Dark,
    Light,
    ;

    fun next(): ThemeMode =
        when (this) {
            System -> Dark
            Dark -> Light
            Light -> System
        }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Suppress("FunctionNaming", "DEPRECATION")
//@Composable
//private fun TitleBarScope.TitleBarIconButton(
//    imageVector: ImageVector,
//    contentDescription: String,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit,
//) {
//    val hoverInteraction = remember { MutableInteractionSource() }
//    val isHovered by hoverInteraction.collectIsHoveredAsState()
//
//    Box(modifier = modifier) {
//        TooltipBox(
//            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
//            tooltip = { PlainTooltip { Text(contentDescription) } },
//            state = rememberTooltipState(),
//        ) {
//            Icon(
//                imageVector = imageVector,
//                contentDescription = contentDescription,
//                tint = MaterialTheme.colorScheme.onSurface,
//                modifier =
//                    Modifier
//                        .padding(horizontal = 4.dp)
//                        .clip(CircleShape)
//                        .hoverable(hoverInteraction)
//                        .background(
//                            if (isHovered) {
//                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
//                            } else {
//                                Color.Transparent
//                            },
//                        ).clickable(
//                            interactionSource = hoverInteraction,
//                            indication = null,
//                        ) { onClick() }
//                        .padding(4.dp)
//                        .size(16.dp),
//            )
//        }
//    }
//}
