package com.example.melitest.presentation.screens.components.results.productDetail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlin.math.floor

@Composable
fun RatingRow(
    rating: Float,
    reviews: Int,
    starColor: Color = Color(0xFFF59E0B),
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        val full = floor(rating).toInt()
        val hasHalf = (rating - full) >= 0.5f
        repeat(5) { i ->
            val icon = when {
                i < full  -> Icons.Rounded.Star
                i == full && hasHalf -> Icons.AutoMirrored.Rounded.StarHalf
                else -> Icons.Rounded.StarBorder
            }
            Icon(icon, contentDescription = null, tint = starColor)
        }
        Text("  ${"%.1f".format(rating)}  ($reviews)", fontSize = 12.sp)
    }
}