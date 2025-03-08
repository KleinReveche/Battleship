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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.revechelizarondo.battleship.core.domain.models.GameType
import com.revechelizarondo.battleship.core.domain.models.PlayerType
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.ui.components.retro.RetroButton
import com.revechelizarondo.battleship.core.ui.components.retro.RetroPanel
import com.revechelizarondo.battleship.core.ui.components.retro.RetroSlider
import com.revechelizarondo.battleship.core.ui.components.retro.RetroStarsEffect
import com.revechelizarondo.battleship.core.ui.resources.Res
import com.revechelizarondo.battleship.core.ui.resources.boardLayoutTime
import com.revechelizarondo.battleship.core.ui.resources.gridSize
import com.revechelizarondo.battleship.core.ui.resources.player1
import com.revechelizarondo.battleship.core.ui.resources.player2
import com.revechelizarondo.battleship.core.ui.resources.selectPlayerType
import com.revechelizarondo.battleship.core.ui.resources.selectShipCount
import com.revechelizarondo.battleship.core.ui.resources.shotsPerTurn
import com.revechelizarondo.battleship.core.ui.resources.timePerTurn
import com.revechelizarondo.battleship.core.ui.verticalScrollAndDrag
import com.revechelizarondo.battleship.feature.config.components.LocalVsComputerDifficultySelector
import com.revechelizarondo.battleship.feature.config.components.ShipQuantity
import org.jetbrains.compose.resources.stringResource

private const val MIN_TIME_PER_TURN_SEC = 10
private const val MAX_TIME_PER_TURN_SEC = 120
private const val MIN_SHOTS_PER_TURN = 1
private const val MAX_SHOTS_PER_TURN = 5
private val GRID_SIZES = listOf(8, 10, 12, 14, 16)

@Composable
fun GameSettings(
    gameType: GameType,
    currentPlayerType: PlayerType,
    changePrimaryPlayerType: (PlayerType) -> Unit,
    shipTypes: Map<Ship, Int>,
    addShip: (Ship) -> Unit,
    removeShip: (Ship) -> Unit,
    gridSize: Int,
    changeGridSize: (Int) -> Unit,
    boardLayoutTimeSeconds: Int,
    changeBoardLayoutTime: (Int) -> Unit,
    shotsPerTurn: Int,
    changeShotsPerTurn: (Int) -> Unit,
    timePerTurnSeconds: Int,
    changeTimePerTurn: (Int) -> Unit,
    difficultyCount: Int,
    difficultyPagerState: PagerState,
    onClickReturn: () -> Unit,
    onClickNext: () -> Unit,
    isExpanded: Boolean,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val currentPlayerTypeOptions =
        listOf(stringResource(Res.string.player1), stringResource(Res.string.player2))

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
                .fillMaxSize()
                .padding(16.dp)
                .alpha(alphaAnim.value)
                .verticalScrollAndDrag(scrollState, scope = scope),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (!isExpanded) {
                    RetroButton(
                        text = "BACK",
                        isSelected = false,
                        onClick = onClickReturn
                    )
                }

                Text(
                    text = when (gameType) {
                        GameType.PLAYER_VS_COMPUTER -> "BATTLESHIP SETTINGS"
                        GameType.ONLINE -> "ONLINE SETTINGS"
                    },
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(shadow = Shadow(color = Color.Blue, blurRadius = 8f))
                )

                Spacer(modifier = Modifier.size(if (!isExpanded) 64.dp else 0.dp, 0.dp))
            }

            if (gameType == GameType.PLAYER_VS_COMPUTER) {
                RetroPanel {
                    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                tint = Color.Cyan,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                "COMPUTER DIFFICULTY",
                                color = Color.Yellow,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        LocalVsComputerDifficultySelector(
                            difficultyCount,
                            scope,
                            difficultyPagerState
                        )

                        Text(
                            text = stringResource(Res.string.selectPlayerType),
                            color = Color.Cyan,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            currentPlayerTypeOptions.forEachIndexed { index, label ->
                                RetroButton(
                                    text = label,
                                    isSelected = currentPlayerType == PlayerType.entries[index],
                                    onClick = { changePrimaryPlayerType(PlayerType.entries[index]) }
                                )
                            }
                        }
                    }
                }
            }

            RetroPanel {
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Icon(
//                            Icons.Default.DirectionsBoat,
//                            contentDescription = null,
//                            tint = Color.Cyan,
//                            modifier = Modifier.padding(end = 8.dp)
//                        )
                        Text(
                            stringResource(Res.string.selectShipCount),
                            color = Color.Yellow,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    ShipQuantity(
                        shipTypes = shipTypes,
                        onShipAdded = { addShip(it) },
                        onShipRemoved = { removeShip(it) }
                    )
                }
            }

            RetroPanel {
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Icon(
//                            Icons.Default.Tune,
//                            contentDescription = null,
//                            tint = Color.Cyan,
//                            modifier = Modifier.padding(end = 8.dp)
//                        )
                        Text(
                            "GAME PARAMETERS",
                            color = Color.Yellow,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    RetroSlider(
                        label = "${stringResource(Res.string.gridSize)}: ${gridSize}x${gridSize}",
                        value = gridSize.toFloat(),
                        onValueChange = { changeGridSize(it.toInt()) },
                        valueRange = GRID_SIZES.first().toFloat()..GRID_SIZES.last().toFloat(),
                        steps = 3
                    )

                    RetroSlider(
                        label = "${stringResource(Res.string.boardLayoutTime)}: $boardLayoutTimeSeconds sec",
                        value = boardLayoutTimeSeconds.toFloat(),
                        onValueChange = { changeBoardLayoutTime(it.toInt()) },
                        valueRange = MIN_TIME_PER_TURN_SEC.toFloat()..MAX_TIME_PER_TURN_SEC.toFloat(),
                        steps = 0
                    )

                    RetroSlider(
                        label = "${stringResource(Res.string.shotsPerTurn)}: $shotsPerTurn",
                        value = shotsPerTurn.toFloat(),
                        onValueChange = { changeShotsPerTurn(it.toInt()) },
                        valueRange = MIN_SHOTS_PER_TURN.toFloat()..MAX_SHOTS_PER_TURN.toFloat(),
                        steps = MAX_SHOTS_PER_TURN - MIN_SHOTS_PER_TURN - 1
                    )

                    RetroSlider(
                        label = "${stringResource(Res.string.timePerTurn)}: $timePerTurnSeconds sec",
                        value = timePerTurnSeconds.toFloat(),
                        onValueChange = { changeTimePerTurn(it.toInt()) },
                        valueRange = MIN_TIME_PER_TURN_SEC.toFloat()..MAX_TIME_PER_TURN_SEC.toFloat(),
                        steps = 0
                    )
                }
            }

            RetroButton(
                text = "NEXT",
                isSelected = true,
                onClick = onClickNext
            )

            Spacer(Modifier.size(0.dp, 16.dp))
        }
    }
}
