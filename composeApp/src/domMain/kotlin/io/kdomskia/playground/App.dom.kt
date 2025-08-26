package io.kdomskia.playground

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kdomskia.compose.foundation.background
import io.kdomskia.compose.foundation.layout.fillMaxWidth
import io.kdomskia.compose.foundation.layout.padding
import io.kdomskia.compose.material3.Text
import io.kdomskia.compose.ui.Modifier
import io.kdomskia.compose.ui.settings.SkiaUiSettingsScope

@Composable
actual fun SkiaUiSettingsScope.skiaCustomSettings() {

}

@Composable
actual fun ExpectActualComposable() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD14E65))
            .padding(2.dp),
        text = "specific logic"
    )
}