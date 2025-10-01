package com.example.melitest.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.melitest.R

@Composable
fun LoginScreen(
    onContinue: (String) -> Unit,
    onNoUser: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    val canContinue = name.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9FC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
            shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp),
            color = Color.White,
            shadowElevation = 70.dp,
            tonalElevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
                    .background(
                        color = Color(0XFFf2d417)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.meli_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Hola!",
                    color = Color(0xFF1F2937),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(50.dp))
                Text(
                    text = "Ingresá tu usuario para comenzar",
                    color = Color(0xFF4B5563),
                    fontSize = 18.sp
                )
            }

            val tfColors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF9CA3AF),
                unfocusedIndicatorColor = Color(0xFFCBD5E1),
                cursorColor = Color(0xFF2563EB),
                focusedTextColor = Color(0xFF111827),
                unfocusedTextColor = Color(0xFF111827),
                focusedPlaceholderColor = Color(0xFF9CA3AF),
                unfocusedPlaceholderColor = Color(0xFF9CA3AF)
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                placeholder = { Text("Nombre o email") },
                singleLine = true,
                shape = RectangleShape,
                colors = tfColors,
                keyboardActions = KeyboardActions(
                    onDone = { if (canContinue) onContinue(name.trim()) }
                )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp)
            ) {
                Button(
                    onClick = { onContinue(name.trim()) },
                    enabled = canContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B82F6),
                        disabledContainerColor = Color(0xFF93C5FD),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text("Comenzar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(12.dp))

                TextButton(
                    onClick = onNoUser,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "No tengo usuario",
                        color = Color(0xFF3B82F6),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
