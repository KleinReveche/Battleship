package com.revechelizarondo.battleship.feature.config

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.revechelizarondo.battleship.core.domain.models.ComputerDifficulty
import com.revechelizarondo.battleship.core.domain.models.GameType
import com.revechelizarondo.battleship.core.ui.components.retro.RetroStarsEffect
import com.revechelizarondo.battleship.feature.config.layouts.GameOptions
import com.revechelizarondo.battleship.feature.config.layouts.GameSettings
import com.revechelizarondo.battleship.feature.config.layouts.PlayerShipsConfig
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.sin
import kotlin.random.Random

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ConfigScreen() {
    val vm: ConfigScreenViewModel = koinViewModel()
    val scope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val difficultyCount by remember { derivedStateOf { ComputerDifficulty.entries.size - 1 } }
    val difficultyPagerState = rememberPagerState(initialPage = 1, pageCount = { difficultyCount })
    val isExpanded =
        currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED

    // Animation state
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    // Background transition animation
    val skyColors = listOf(
        Color(0xFF000B32), // Deep space
        Color(0xFF0A2472), // Space blue
        Color(0xFF1E3B70), // Transition
        Color(0xFF4189DD), // Sky blue
        Color(0xFF76B0F1)  // Light sky
    )

    val portColors = listOf(
        Color(0xFF4189DD), // Sky blue
        Color(0xFF76B0F1), // Light sky
        Color(0xFF8EC7FF), // Horizon
        Color(0xFF0077BE), // Ocean blue
        Color(0xFF005B8E)  // Deep sea
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Animated background
        AnimatedContent(
            targetState = vm.showGameOptions,
            label = "background_transition",
            transitionSpec = {
                slideInVertically(tween(500)) { height -> height } togetherWith
                        slideOutVertically(tween(500)) { height -> -height }
            }
        ) { isGameOptions ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = if (isGameOptions) skyColors else portColors
                        )
                    )
            ) {
                if (isGameOptions) {
                    RetroStarsEffect()
                } else {
                    RetroPortEffect()
                }
            }
        }

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .alpha(alphaAnim.value)
        ) {
            AnimatedContent(vm.showGameOptions) { showGameOptions ->
                AnimatedVisibility(
                    showGameOptions,
                    exit = slideOutVertically() + shrinkVertically()
                ) {
                    ListDetailPaneScaffold(
                        directive = navigator.scaffoldDirective,
                        value = navigator.scaffoldValue,
                        listPane = {
                            AnimatedPane(
                                exitTransition = slideOutHorizontally(tween(1000))
                            ) {
                                GameOptions(
                                    selectedPlayModeIndex = vm.selectedPlayIndex,
                                    onSelectedPlayModeIndexChanged = { vm.selectedPlayIndex = it },
                                    selectedSkinIndex = vm.selectedSkinIndex,
                                    onSelectedSkinIndexChanged = { vm.selectedSkinIndex = it },
                                    player1Name = vm.player1Name,
                                    onPlayer1NameChanged = { vm.player1Name = it },
                                    player2Name = vm.player2Name,
                                    onPlayer2NameChanged = { vm.player2Name = it },
                                    onNextPane = {
                                        vm.populateShipTypes()
                                        scope.launch {
                                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                        }
                                    },
                                    isExpanded = isExpanded
                                )
                            }
                        },
                        detailPane = {
                            AnimatedPane(
                                enterTransition = slideInHorizontally(tween(700)) { it }
                            ) {
                                GameSettings(
                                    gameType = GameType.entries[vm.selectedPlayIndex],
                                    currentPlayerType = vm.playingPlayer,
                                    changePrimaryPlayerType = { vm.playingPlayer = it },
                                    shipTypes = vm.shipTypes,
                                    addShip = { vm.addShip(it) },
                                    removeShip = { vm.removeShip(it) },
                                    gridSize = vm.gridSize,
                                    changeGridSize = { vm.gridSize = it },
                                    boardLayoutTimeSeconds = vm.boardLayoutTimeSeconds,
                                    changeBoardLayoutTime = { vm.boardLayoutTimeSeconds = it },
                                    shotsPerTurn = vm.shotsPerTurn,
                                    changeShotsPerTurn = { vm.shotsPerTurn = it },
                                    timePerTurnSeconds = vm.timePerTurnSeconds,
                                    changeTimePerTurn = { vm.timePerTurnSeconds = it },
                                    difficultyCount = difficultyCount,
                                    difficultyPagerState = difficultyPagerState,
                                    onClickReturn = {
                                        scope.launch {
                                            navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                                        }
                                    },
                                    onClickNext = { vm.showGameOptions = false },
                                    isExpanded = isExpanded
                                )
                            }
                        }
                    )
                }
                AnimatedVisibility(
                    !showGameOptions,
                    enter = slideInVertically(tween(500))
                ) {
                    PlayerShipsConfig()
                }
            }
        }
    }
}

@Composable
private fun RetroPortEffect() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val random = Random(1) // Using fixed seed for consistency

        // Draw sky glow
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFD700).copy(alpha = 0.1f),
                    Color.Transparent
                ),
                startY = 0f,
                endY = size.height * 0.4f
            ),
            size = Size(size.width, size.height * 0.4f)
        )

        // Draw land mass (shore and hills)
        val shoreline = size.height * 0.75f

        // Draw distant mountains
        for (i in 0..8) {
            val mountainHeight = random.nextFloat() * size.height * 0.15f + size.height * 0.05f
            val mountainWidth = random.nextFloat() * size.width * 0.2f + size.width * 0.1f
            val mountainX = random.nextFloat() * size.width * 0.8f

            // Create mountain path properly
            val mountainPath = Path().apply {
                moveTo(mountainX, shoreline)
                lineTo(mountainX + mountainWidth / 2, shoreline - mountainHeight)
                lineTo(mountainX + mountainWidth, shoreline)
                close()
            }

            drawPath(
                path = mountainPath,
                color = Color(0xFF2E4435).copy(alpha = 0.7f)
            )
        }

        // Draw main land mass
        drawRect(
            color = Color(0xFF3D5042),
            topLeft = Offset(0f, shoreline),
            size = Size(size.width, size.height - shoreline)
        )

        // Draw sand near shore
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFE2C992),
                    Color(0xFF3D5042)
                ),
                startY = shoreline,
                endY = shoreline + 40f
            ),
            topLeft = Offset(0f, shoreline),
            size = Size(size.width, 40f)
        )

        // Draw port structures on the land
        for (i in 0..5) {
            val height = size.height * (0.75f - random.nextFloat() * 0.1f)
            val width = random.nextFloat() * size.width * 0.15f + size.width * 0.05f
            val xPos = size.width * 0.3f + i * size.width * 0.1f

            // Building
            drawRect(
                color = Color(0xFF1A2639).copy(alpha = 0.9f),
                topLeft = Offset(xPos, height),
                size = Size(width, shoreline - height)
            )

            // Windows
            for (j in 0..2) {
                for (k in 0..1) {
                    if (random.nextFloat() > 0.3f) {
                        drawRect(
                            color = Color.Yellow.copy(alpha = 0.7f),
                            topLeft = Offset(
                                xPos + width * 0.2f + j * width * 0.25f,
                                height + (shoreline - height) * 0.2f + k * (shoreline - height) * 0.3f
                            ),
                            size = Size(width * 0.15f, (shoreline - height) * 0.2f)
                        )
                    }
                }
            }
        }

        // Draw piers extending into water
        for (i in 0..3) {
            val pierWidth = 15f
            val pierLength = random.nextFloat() * size.width * 0.2f + size.width * 0.1f
            val pierX = size.width * 0.2f + i * size.width * 0.2f

            drawRect(
                color = Color(0xFF5D4037),
                topLeft = Offset(pierX, shoreline),
                size = Size(pierWidth, pierLength)
            )
        }

        // Draw boats
        for (i in 0..4) {
            val x = random.nextFloat() * size.width * 0.6f + size.width * 0.2f
            val y = shoreline + 30f + i * 20f
            val boatWidth = random.nextFloat() * 80f + 40f
            val boatHeight = 15f

            // Boat hull
            drawRect(
                color = listOf(Color(0xFF8B4513), Color(0xFF3E2723), Color(0xFF1A237E))[i % 3],
                topLeft = Offset(x, y),
                size = Size(boatWidth, boatHeight)
            )

            // Boat cabin
            if (random.nextFloat() > 0.3f) {
                drawRect(
                    color = Color.White.copy(alpha = 0.8f),
                    topLeft = Offset(x + boatWidth * 0.3f, y - boatHeight),
                    size = Size(boatWidth * 0.4f, boatHeight)
                )
            }
        }

        // Draw simple waves
        for (i in 0..10) {
            val y = shoreline + 10f + i * 10f
            drawLine(
                color = Color.White.copy(alpha = 0.2f),
                start = Offset(0f, y),
                end = Offset(size.width, y + sin(i.toFloat()) * 5f),
                strokeWidth = 2f
            )
        }
    }
}