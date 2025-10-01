package com.example.melitest.domain.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun formatMoney(value: Double, currency: String): String {
    val rounded = "%.2f".format(value)
    val symbol = when (currency.uppercase()) {
        "ARS" -> "$"
        "BRL" -> "R$"
        "CLP" -> "$"
        "MXN" -> "$"
        "COP" -> "$"
        "USD" -> "$"
        else  -> "$"
    }
    return "$symbol$rounded"
}

@Composable
fun ExpandableText(text: String, minimizedMaxLines: Int = 3) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val showMore = remember(text) { text.length > 140 }

    Text(
        text = text,
        maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
        fontSize = 14.sp,
        color = Color(0xFF111827)
    )
    if (showMore) {
        Spacer(Modifier.height(4.dp))
        Text(
            text = if (expanded) "Mostrar menos" else "Leer más…",
            color = Color(0xFF2563EB),
            fontSize = 14.sp,
            modifier = Modifier.clickable { expanded = !expanded }
        )
    }
}