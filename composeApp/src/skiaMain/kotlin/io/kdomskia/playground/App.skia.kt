package io.kdomskia.playground

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.svg.SvgDecoder
import io.kdomskia.compose.foundation.background
import io.kdomskia.compose.foundation.layout.fillMaxWidth
import io.kdomskia.compose.foundation.layout.padding
import io.kdomskia.compose.material3.Text
import io.kdomskia.compose.ui.Modifier
import io.kdomskia.compose.ui.settings.SkiaUiSettingsScope

@Composable
actual fun SkiaUiSettingsScope.skiaCustomSettings() {
    coilImageLoader { context ->
        ImageLoader.Builder(context)
            .components {
                add(
                    SvgDecoder.Factory(
                        useViewBoundsAsIntrinsicSize = false,
                        renderToBitmap = true,
                        scaleToDensity = true
                    )
                )
            }
            .build()
    }
}

@Composable
actual fun ExpectActualComposable() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6858F6))
            .padding(2.dp),
        text = "specific logic"
    )
}