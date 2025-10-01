package com.example.melitest.presentation.screens.components.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.melitest.R

@Composable
fun ResultsHeader(
    name: String,
    initialQuery: String,
    onSearch: (String) -> Unit,
) {
    var queryField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = initialQuery, selection = TextRange(initialQuery.length)))
    }
    val chips = listOf("Pesas","Musica","Tablets","Computadores","Neveras","Cocina","Piscina","Juguetes","Audio","Medicina")
    var selectedChip by rememberSaveable { mutableStateOf<String?>(chips.firstOrNull { it.equals(initialQuery, true) }) }

    LaunchedEffect(initialQuery) {
        if (initialQuery != queryField.text) {
            queryField = TextFieldValue(initialQuery, selection = TextRange(initialQuery.length))
            selectedChip = chips.firstOrNull { it.equals(initialQuery, ignoreCase = true) }
        }
    }

    fun performSearch(q: String) {
        val trimmed = q.trim()
        queryField = TextFieldValue(trimmed, selection = TextRange(trimmed.length))
        onSearch(trimmed)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_color),
                contentDescription = "Mercado Libre",
                modifier = Modifier
                    .height(60.dp)
                    .padding(start = 4.dp, end = 8.dp),
                contentScale = ContentScale.Fit
            )
            Text("mercado\nlibre", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFAD24A)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Perfil",
                    tint = Color(0xFF0B57D0),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Hola, $name!", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 4.dp))
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, null, tint = Color(0xFF6B7280))
            Text("Ingresa tu ubicación", color = Color(0xFF6B7280))
        }

        Spacer(Modifier.height(10.dp))
        TextField(
            value = queryField,
            onValueChange = {
                queryField = it
                if (selectedChip != null && !it.text.equals(selectedChip, ignoreCase = true)) {
                    selectedChip = null
                }
            },
            placeholder = { Text("Buscar productos, marcas y más") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(14.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF3F4F6),
                unfocusedContainerColor = Color(0xFFF3F4F6),
                disabledContainerColor = Color(0xFFF3F4F6),
                cursorColor = Color(0xFF0B57D0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { performSearch(queryField.text) })
        )

        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(chips) { label ->
                val isSelected = selectedChip == label
                Chip(
                    text = label,
                    selected = isSelected,
                    onClick = {
                        if (isSelected) {
                            selectedChip = null
                            queryField = TextFieldValue("", selection = TextRange(0))
                            onSearch("")
                        } else {
                            selectedChip = label
                            performSearch(label)
                        }
                    }
                )
            }
        }
    }
}


