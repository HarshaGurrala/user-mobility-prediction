package com.usermobilityprediction.app.dev

import androidx.compose.runtime.mutableStateListOf

object DevDebug {
    private val _lines = mutableStateListOf<String>()
    val lines: List<String> get() = _lines

    fun log(msg: String) {
        try {
            _lines.add(0, "[${System.currentTimeMillis()}] $msg")
            if (_lines.size > 200) _lines.removeAt(_lines.lastIndex)
        } catch (_: Exception) { }
    }

    fun clear() {
        _lines.clear()
    }
}
