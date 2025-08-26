package io.kdomskia.playground.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SampleViewModel : ViewModel() {

    private val _state = MutableStateFlow(0)

    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                _state.value = _state.value + 1
                delay(1.seconds)
            }
        }
    }

}