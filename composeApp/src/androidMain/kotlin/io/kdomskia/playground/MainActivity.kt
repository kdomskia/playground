package io.kdomskia.playground

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextCompat.getColor(this, R.color.white)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.auto(
                lightScrim = ContextCompat.getColor(this, io.kdomskia.playground.R.color.status_bar_light),
                darkScrim = ContextCompat.getColor(this, io.kdomskia.playground.R.color.status_bar_dark)
            )
        )
        setContent {
            App()
        }
    }

}