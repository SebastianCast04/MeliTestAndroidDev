package com.example.melitest.presentation.screens.components.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LoadingGridSkeleton(padding: PaddingValues) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(Color(0xFFF6F9FC)),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(6) {
            Box(
                modifier = Modifier
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE5E7EB))
            )
        }
    }
}

@Composable
fun EmptyState(padding: PaddingValues, onRetry: () -> Unit) {
    Box(
        Modifier
            .padding(padding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("No encontramos resultados", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onRetry) { Text("Intentar de nuevo") }
        }
    }
}

@Composable
fun ErrorState(msg: String, padding: PaddingValues, onRetry: () -> Unit) {
    Box(
        Modifier
            .padding(padding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Ups… algo salió mal", fontWeight = FontWeight.Bold, color = Color(0xFFB91C1C))
            Spacer(Modifier.height(6.dp))
            Text(msg, color = Color(0xFF6B7280))
            Spacer(Modifier.height(12.dp))
            Button(onClick = onRetry) { Text("Reintentar") }
        }
    }
}
