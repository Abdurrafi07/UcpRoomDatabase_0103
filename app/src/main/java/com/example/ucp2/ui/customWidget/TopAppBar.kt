package com.example.ucp2.ui.customWidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    judul: String,
    modifier: Modifier = Modifier,
    backgroundColor:Color = Color(0xFF4CAF50), // Primary background color
    contentColor: Color = Color.White // Text and icon color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        if (showBackButton) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onBack,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = contentColor
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Kembali",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Centered title text
        Text(
            text = judul,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}