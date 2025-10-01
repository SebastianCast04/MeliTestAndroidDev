package com.example.melitest.presentation.screens.results.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.melitest.domain.models.ProductDetail
import com.example.melitest.domain.util.ExpandableText
import com.example.melitest.domain.util.FakeData
import com.example.melitest.domain.util.formatMoney
import com.example.melitest.presentation.screens.components.results.productDetail.RatingRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    detail: ProductDetail,
    onBack: () -> Unit
) {
    val images = detail.pictures.ifEmpty { listOf() }
    var selected by rememberSaveable { mutableIntStateOf(0) }
    val rating = remember(detail.id) { FakeData.rating(detail.id) }
    val reviews = remember(detail.id) { FakeData.reviews(detail.id) }
    val priceToShow = detail.price ?: FakeData.price(detail.id)
    val currToShow = detail.currency ?: FakeData.currencyFromSite("MCO")



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(detail.title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Fav")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color(0xFFF8FAFC))
                ) {
                    if (images.isNotEmpty()) {
                        AsyncImage(
                            model = images[selected],
                            contentDescription = detail.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center
                        )
                    } else {
                        Text(
                            "Sin imágenes",
                            color = Color(0xFF9CA3AF),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            if (images.size > 1) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(images.size) { i ->
                        val isSel = i == selected
                        AsyncImage(
                            model = images[i],
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    width = if (isSel) 2.dp else 1.dp,
                                    color = if (isSel) Color(0xFF111827) else Color(0xFFE5E7EB),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { selected = i },
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            Column(Modifier.padding(horizontal = 16.dp)) {

                Text(detail.title, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)

                if (!detail.category.isNullOrBlank()) {
                    Text(
                        detail.category,
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(top = 14.dp)
                    )
                }

                RatingRow(
                    rating = rating,
                    reviews = reviews,
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .offset(x = (-4).dp)
                )
            }


            Spacer(Modifier.height(12.dp))
            Text(
                text = formatMoney(priceToShow, currToShow),
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )


            Spacer(Modifier.height(12.dp))

            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Descripción", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(Modifier.height(6.dp))
                val desc = detail.shortDescription
                    ?: buildAutoDescription(detail.attributes)
                ExpandableText(text = desc)
            }

            if (detail.attributes.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text("Especificaciones", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(Modifier.height(6.dp))
                    detail.attributes.entries.take(6).forEach { (k, v) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(k, color = Color(0xFF6B7280))
                            Text(v, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

private fun buildAutoDescription(attrs: Map<String, String>): String {
    if (attrs.isEmpty()) return "Producto de catálogo Mercado Libre."
    val keys = listOf("Marca", "Modelo", "Línea", "Color")
    val picked = keys.mapNotNull { k -> attrs[k]?.let { "$k: $it" } }
    return if (picked.isNotEmpty())
        picked.joinToString(" · ")
    else
        attrs.entries.take(4).joinToString(" · ") { "${it.key}: ${it.value}" }
}
