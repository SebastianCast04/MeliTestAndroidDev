package com.example.melitest.presentation.screens.components.results

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Chip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) Color(0xFFFAD24A) else Color(0xFFF1F5F9)
    val textColor = if (selected) Color(0xFF1F2937) else Color(0xFF475569)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Text(text, fontSize = 12.sp, color = textColor, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
    }
}