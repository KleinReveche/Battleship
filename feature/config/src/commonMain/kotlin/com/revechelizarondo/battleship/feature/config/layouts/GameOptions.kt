package com.revechelizarondo.battleship.feature.config.layouts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelButton
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelContainerColors
import com.revechelizarondo.battleship.core.ui.components.retro.RetroPanel
import com.revechelizarondo.battleship.core.ui.components.retro.RetroStarsEffect
import com.revechelizarondo.battleship.core.ui.components.retro.RetroTextField
import com.revechelizarondo.battleship.core.ui.icons.SportsEsports
import com.revechelizarondo.battleship.core.ui.icons.ToysAndGames
import com.revechelizarondo.battleship.core.ui.resources.Res
import com.revechelizarondo.battleship.core.ui.resources.classicMode
import com.revechelizarondo.battleship.core.ui.resources.gameType
import com.revechelizarondo.battleship.core.ui.resources.hackerMode
import com.revechelizarondo.battleship.core.ui.resources.localVsComputer
import com.revechelizarondo.battleship.core.ui.resources.online
import com.revechelizarondo.battleship.core.ui.resources.playMode
import com.revechelizarondo.battleship.core.ui.resources.player
import com.revechelizarondo.battleship.core.ui.resources.player1Name
import com.revechelizarondo.battleship.core.ui.resources.player2Name
import com.revechelizarondo.battleship.core.ui.theme.OnPixelButtonTextColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameOptions(
    navigateUp: () -> Unit,
    selectedPlayModeIndex: Int,
    onSelectedPlayModeIndexChanged: (Int) -> Unit,
    selectedSkinIndex: Int,
    onSelectedSkinIndexChanged: (Int) -> Unit,
    player1Name: String,
    onPlayer1NameChanged: (String) -> Unit,
    player2Name: String,
    onPlayer2NameChanged: (String) -> Unit,
    onNextPane: () -> Unit = {},
    onNextPaneEnabled: Boolean,
    isExpanded: Boolean
) {
    val playOptions = listOf(
        stringResource(Res.string.localVsComputer),
        stringResource(Res.string.online)
    )

    val skinOptions = listOf(
        stringResource(Res.string.classicMode),
        stringResource(Res.string.hackerMode)
    )

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000B32), // Deep space
                        Color(0xFF0A2472), // Space blue
                        Color(0xFF1E3B70), // Transition
                        Color(0xFF4189DD), // Sky blue
                        Color(0xFF76B0F1)  // Light sky
                    )
                )
            )
    ) {
        RetroStarsEffect()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(alphaAnim.value),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.fillMaxWidth().height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PixelButton(
                    onClick = navigateUp,
                    cornerSize = 2,
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = OnPixelButtonTextColor
                    )
                }

                Spacer(Modifier.width(16.dp))

                Text(
                    text = "BATTLESHIP SETUP",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = TextStyle(shadow = Shadow(color = Color.Blue, blurRadius = 8f))
                )
            }

            // Game Type panel
            RetroPanel {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            SportsEsports,
                            contentDescription = null,
                            tint = Color.Cyan,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            stringResource(Res.string.gameType),
                            color = Color.Yellow,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        playOptions.forEachIndexed { index, label ->
                            PixelButton(
                                enabled = index != 1,
                                onClick = { onSelectedPlayModeIndexChanged(index) },
                                cornerSize = 2,
                                colors = if (index == selectedPlayModeIndex) PixelContainerColors.NConsoleCompanyPixelContainerColors
                                else PixelContainerColors.GreyPixelContainerColors
                            ) {
                                Text(label, color = OnPixelButtonTextColor)
                            }
                        }
                    }
                }
            }

            // Play Mode panel
            RetroPanel {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            ToysAndGames,
                            contentDescription = null,
                            tint = Color.Cyan,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            stringResource(Res.string.playMode),
                            color = Color.Yellow,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        skinOptions.forEachIndexed { index, label ->
                            PixelButton(
                                enabled = true,
                                onClick = { onSelectedSkinIndexChanged(index) },
                                cornerSize = 2,
                                colors = if (index == selectedSkinIndex) PixelContainerColors.NConsoleCompanyPixelContainerColors
                                else PixelContainerColors.GreyPixelContainerColors
                            ) {
                                Text(label, color = OnPixelButtonTextColor)
                            }
                        }
                    }
                }
            }

            // Player names panel
            RetroPanel {
                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    RetroTextField(
                        value = player1Name,
                        onValueChange = onPlayer1NameChanged,
                        label = if (selectedPlayModeIndex == 1) stringResource(Res.string.player1Name)
                        else stringResource(Res.string.player)
                    )

                    if (selectedPlayModeIndex == 1) {
                        Spacer(Modifier.height(12.dp))
                        RetroTextField(
                            value = player2Name,
                            onValueChange = onPlayer2NameChanged,
                            label = stringResource(Res.string.player2Name)
                        )
                    }
                }
            }

            if (!isExpanded) {
                PixelButton(
                    enabled = onNextPaneEnabled,
                    onClick = onNextPane,
                    cornerSize = 4,
                    colors = PixelContainerColors.NConsoleCompanyPixelContainerColors
                ) {
                    Text("NEXT", fontWeight = FontWeight.Bold, color = OnPixelButtonTextColor)
                }
            }
        }
    }
}