package com.example.melitest.presentation.screens.components.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BigPromoBanner(
    modifier: Modifier = Modifier,
    title: String = "Ofertas del día",
    subtitle: String = "Hasta 40% OFF en audífonos",
    ctaText: String = "Ver más",
    imageRes: Int? = null,
    imageUrl: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFF7CC),
                            Color(0xFFFAD24A)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1F2937)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = Color(0xFF374151)
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1F2937),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(text = ctaText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x33FFFFFF)),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        imageRes != null -> {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().padding(6.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        imageUrl != null -> {
                            coil.compose.AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().padding(6.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}

