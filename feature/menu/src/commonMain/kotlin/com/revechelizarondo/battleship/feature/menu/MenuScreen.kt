package com.revechelizarondo.battleship.feature.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revechelizarondo.battleship.feature.menu.components.MenuButton
import kotlin.random.Random

@Composable
fun MenuScreen(
    goToConfig: () -> Unit,
    goToLeaderboard: () -> Unit,
    goToSettings: () -> Unit,
    modifier: Modifier
) {
    val spaceBackground = Color(0xFF080810)
    val titleGradient = listOf(Color(0xFFFF00FF), Color(0xFFFF5500), Color(0xFFFFFF00))

    val stars = remember {
        val starCount = 220
        List(starCount) {
            val x = Random.nextFloat()
            val y = Random.nextFloat()
            val size = Random.nextFloat() * 0.002f + 0.0005f
            val alpha = Random.nextFloat() * 0.7f + 0.3f

            Quadruple(x, y, size, alpha)
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToSettings,
                containerColor = Color(0xFF551A8B),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
        },
        modifier = modifier
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(spaceBackground)
                .drawBehind {
                    stars.forEach { (xPercent, yPercent, sizePercent, alpha) ->
                        val x = xPercent * size.width
                        val y = yPercent * size.height
                        val starSize = sizePercent * size.width.coerceAtLeast(size.height)

                        drawCircle(
                            color = Color.White.copy(alpha = alpha),
                            radius = starSize,
                            center = Offset(x, y)
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(0.1f))

                Text(
                    text = "BATTLESHIP",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.linearGradient(titleGradient),
                        shadow = Shadow(
                            color = Color(0xFF8000FF),
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        ),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.weight(0.2f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    CenteredArrowButton("PLAY", goToConfig)
                    CenteredArrowButton("RANKING", goToLeaderboard)
                    CenteredArrowButton("ABOUT") {}
                }

                Spacer(modifier = Modifier.weight(0.2f))
            }
        }
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun CenteredArrowButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "▶",
                color = Color.Yellow,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xFF8000FF).copy(alpha = 0.5f),
                        offset = Offset(1f, 1f),
                        blurRadius = 2f
                    )
                )
            )
        }
    }
}