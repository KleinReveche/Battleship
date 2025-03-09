package com.revechelizarondo.battleship.feature.config.layouts

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.revechelizarondo.battleship.core.domain.models.BattleshipBoard
import com.revechelizarondo.battleship.core.domain.models.Cell
import com.revechelizarondo.battleship.core.domain.models.GridCoordinates
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.PlacedShip
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.ui.BackHandler
import com.revechelizarondo.battleship.core.ui.components.DraggableScreen
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelButton
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelContainerColors
import com.revechelizarondo.battleship.core.ui.icons.PlayArrow
import com.revechelizarondo.battleship.core.ui.icons.ResetAlt
import com.revechelizarondo.battleship.core.ui.icons.Shuffle
import com.revechelizarondo.battleship.core.ui.resources.Res
import com.revechelizarondo.battleship.core.ui.resources.play
import com.revechelizarondo.battleship.core.ui.resources.randomizeBoard
import com.revechelizarondo.battleship.core.ui.resources.resetBoard
import com.revechelizarondo.battleship.core.ui.theme.OnPixelButtonTextColor
import com.revechelizarondo.battleship.feature.config.components.DraggableShipView
import com.revechelizarondo.battleship.feature.config.components.PlayerGridDropZone
import org.jetbrains.compose.resources.stringResource

enum class ScreenSizeClass { COMPACT, MEDIUM, EXPANDED }

@Composable
fun getScreenSizeClass(): ScreenSizeClass {
    val windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()

    return when (windowInfo.windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> ScreenSizeClass.COMPACT
        WindowWidthSizeClass.EXPANDED -> ScreenSizeClass.EXPANDED
        else -> ScreenSizeClass.MEDIUM
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerShipsConfig(
    navigateToGame: () -> Unit,
    returnToGameParams: () -> Unit,
    board: BattleshipBoard,
    shipTypes: Map<Ship, Int>,
    getCellInBound: () -> GridCoordinates?,
    getShipFromCell: (x: Int, y: Int) -> PlacedShip?,
    getShipCells: (gridCoordinates: GridCoordinates) -> Map<GridCoordinates, Cell>,
    isShipInBounds: (gridCoordinates: GridCoordinates) -> Boolean,
    updateCellInBound: (GridCoordinates) -> Unit,
    placeShip: (ship: Ship, shipIndex: Int, x: Int, y: Int) -> Unit,
    randomizeShips: () -> Unit,
    resetShips: () -> Unit,
    startDragging: (orientation: Orientation, ship: Ship) -> Unit,
    stopDragging: () -> Unit,
) {
    val screenSizeClass = getScreenSizeClass()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "PLAYER SHIPS",
                        color = Color(0xFF1E3B70),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(shadow = Shadow(color = Color.Blue, blurRadius = 8f))
                    )
                },
                navigationIcon = {
                    PixelButton(
                        onClick = returnToGameParams,
                        cornerSize = 2,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = OnPixelButtonTextColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            if (shipTypes.values.all { it == 0 }) {
                PixelButton(
                    onClick = navigateToGame,
                    cornerSize = 1,
                    colors = PixelContainerColors.NConsoleCompanyPixelContainerColors
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            PlayArrow,
                            contentDescription = null,
                            tint = OnPixelButtonTextColor,
                            modifier = Modifier.size(36.dp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))

                        Text(
                            text = stringResource(Res.string.play),
                            color = OnPixelButtonTextColor,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        DraggableScreen {
            BackHandler(returnToGameParams)

            val scrollState = rememberScrollState()

            when (screenSizeClass) {
                ScreenSizeClass.COMPACT -> CompactPlayerShipLayout(
                    board = board,
                    getCellInBound = getCellInBound,
                    getShipFromCell = getShipFromCell,
                    getShipCells = getShipCells,
                    isShipInBounds = isShipInBounds,
                    updateCellInBound = updateCellInBound,
                    placeShip = placeShip,
                    paddingValues = paddingValues,
                    scrollState = scrollState,
                    shipTypes = shipTypes,
                    randomizeShips = randomizeShips,
                    resetShips = resetShips,
                    startDragging = startDragging,
                    stopDragging = stopDragging
                )

                ScreenSizeClass.MEDIUM -> MediumPlayerShipLayout(
                    board = board,
                    getCellInBound = getCellInBound,
                    getShipFromCell = getShipFromCell,
                    getShipCells = getShipCells,
                    isShipInBounds = isShipInBounds,
                    updateCellInBound = updateCellInBound,
                    placeShip = placeShip,
                    paddingValues = paddingValues,
                    shipTypes = shipTypes,
                    randomizeShips = randomizeShips,
                    resetShips = resetShips,
                    startDragging = startDragging,
                    stopDragging = stopDragging
                )

                ScreenSizeClass.EXPANDED -> ExpandedPlayerShipLayout(
                    board = board,
                    getCellInBound = getCellInBound,
                    getShipFromCell = getShipFromCell,
                    getShipCells = getShipCells,
                    isShipInBounds = isShipInBounds,
                    updateCellInBound = updateCellInBound,
                    placeShip = placeShip,
                    paddingValues = paddingValues,
                    shipTypes = shipTypes,
                    randomizeShips = randomizeShips,
                    resetShips = resetShips,
                    startDragging = startDragging,
                    stopDragging = stopDragging
                )
            }
        }
    }
}


@Composable
private fun CompactPlayerShipLayout(
    board: BattleshipBoard,
    shipTypes: Map<Ship, Int>,
    getCellInBound: () -> GridCoordinates?,
    getShipFromCell: (x: Int, y: Int) -> PlacedShip?,
    getShipCells: (gridCoordinates: GridCoordinates) -> Map<GridCoordinates, Cell>,
    isShipInBounds: (gridCoordinates: GridCoordinates) -> Boolean,
    updateCellInBound: (GridCoordinates) -> Unit,
    placeShip: (ship: Ship, shipIndex: Int, x: Int, y: Int) -> Unit,
    randomizeShips: () -> Unit,
    resetShips: () -> Unit,
    startDragging: (orientation: Orientation, ship: Ship) -> Unit,
    stopDragging: () -> Unit,
    paddingValues: PaddingValues,
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        PlayerShipScreenContent(
            board = board,
            getCellInBound = getCellInBound,
            getShipFromCell = getShipFromCell,
            getShipCells = getShipCells,
            isShipInBounds = isShipInBounds,
            updateCellInBound = updateCellInBound,
            placeShip = placeShip,
            shipTypes = shipTypes,
            randomizeShips = randomizeShips,
            resetShips = resetShips,
            startDragging = startDragging,
            stopDragging = stopDragging,
            layoutType = ScreenSizeClass.COMPACT
        )
    }
}

@Composable
private fun MediumPlayerShipLayout(
    board: BattleshipBoard,
    shipTypes: Map<Ship, Int>,
    getCellInBound: () -> GridCoordinates?,
    getShipFromCell: (x: Int, y: Int) -> PlacedShip?,
    getShipCells: (gridCoordinates: GridCoordinates) -> Map<GridCoordinates, Cell>,
    isShipInBounds: (gridCoordinates: GridCoordinates) -> Boolean,
    updateCellInBound: (GridCoordinates) -> Unit,
    placeShip: (ship: Ship, shipIndex: Int, x: Int, y: Int) -> Unit,
    randomizeShips: () -> Unit,
    resetShips: () -> Unit,
    startDragging: (orientation: Orientation, ship: Ship) -> Unit,
    stopDragging: () -> Unit,
    paddingValues: PaddingValues
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerShipScreenContent(
            board = board,
            getCellInBound = getCellInBound,
            getShipFromCell = getShipFromCell,
            getShipCells = getShipCells,
            isShipInBounds = isShipInBounds,
            updateCellInBound = updateCellInBound,
            placeShip = placeShip,
            shipTypes = shipTypes,
            randomizeShips = randomizeShips,
            resetShips = resetShips,
            startDragging = startDragging,
            stopDragging = stopDragging,
            layoutType = ScreenSizeClass.MEDIUM
        )
    }
}

@Composable
private fun ExpandedPlayerShipLayout(
    board: BattleshipBoard,
    shipTypes: Map<Ship, Int>,
    getCellInBound: () -> GridCoordinates?,
    getShipFromCell: (x: Int, y: Int) -> PlacedShip?,
    getShipCells: (gridCoordinates: GridCoordinates) -> Map<GridCoordinates, Cell>,
    isShipInBounds: (gridCoordinates: GridCoordinates) -> Boolean,
    updateCellInBound: (GridCoordinates) -> Unit,
    placeShip: (ship: Ship, shipIndex: Int, x: Int, y: Int) -> Unit,
    randomizeShips: () -> Unit,
    resetShips: () -> Unit,
    startDragging: (orientation: Orientation, ship: Ship) -> Unit,
    stopDragging: () -> Unit,
    paddingValues: PaddingValues
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerShipScreenContent(
            board = board,
            getCellInBound = getCellInBound,
            getShipFromCell = getShipFromCell,
            getShipCells = getShipCells,
            isShipInBounds = isShipInBounds,
            updateCellInBound = updateCellInBound,
            placeShip = placeShip,
            shipTypes = shipTypes,
            randomizeShips = randomizeShips,
            resetShips = resetShips,
            startDragging = startDragging,
            stopDragging = stopDragging,
            layoutType = ScreenSizeClass.EXPANDED
        )
    }
}

@Composable
private fun PlayerShipScreenContent(
    board: BattleshipBoard,
    shipTypes: Map<Ship, Int>,
    getCellInBound: () -> GridCoordinates?,
    getShipFromCell: (x: Int, y: Int) -> PlacedShip?,
    getShipCells: (gridCoordinates: GridCoordinates) -> Map<GridCoordinates, Cell>,
    isShipInBounds: (gridCoordinates: GridCoordinates) -> Boolean,
    updateCellInBound: (GridCoordinates) -> Unit,
    placeShip: (ship: Ship, shipIndex: Int, x: Int, y: Int) -> Unit,
    randomizeShips: () -> Unit,
    resetShips: () -> Unit,
    startDragging: (orientation: Orientation, ship: Ship) -> Unit,
    stopDragging: () -> Unit,
    layoutType: ScreenSizeClass
) {
    var cellSize by remember { mutableStateOf(0.dp) }

    when (layoutType) {
        ScreenSizeClass.COMPACT -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BoardSection(
                    board = board,
                    cellSize = cellSize,
                    getCellInBound = getCellInBound,
                    getShipFromCell = getShipFromCell,
                    getShipCells = getShipCells,
                    isShipInBounds = isShipInBounds,
                    updateCellInBound = updateCellInBound,
                    placeShip = placeShip,
                    onCellSizeCalculated = { cellSize = it },
                    divider = 1.0f
                )

                Spacer(modifier = Modifier.padding(8.dp))

                DraggableShipView(
                    shipTypes = shipTypes,
                    startDragging = { ship, orientation -> startDragging(orientation, ship) },
                    stopDragging = { stopDragging() }
                )

                ButtonRow(randomizeShips, resetShips)
            }
        }

        ScreenSizeClass.MEDIUM -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BoardSection(
                    board = board,
                    cellSize = cellSize,
                    getCellInBound = getCellInBound,
                    getShipFromCell = getShipFromCell,
                    getShipCells = getShipCells,
                    isShipInBounds = isShipInBounds,
                    updateCellInBound = updateCellInBound,
                    placeShip = placeShip,
                    onCellSizeCalculated = { cellSize = it },
                    divider = 2.2f
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DraggableShipView(
                        shipTypes = shipTypes,
                        startDragging = { ship, orientation -> startDragging(orientation, ship) },
                        stopDragging = { stopDragging() }
                    )

                    ButtonRow(randomizeShips, resetShips)
                }
            }
        }

        ScreenSizeClass.EXPANDED -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BoardSection(
                    board = board,
                    cellSize = cellSize,
                    getCellInBound = getCellInBound,
                    getShipFromCell = getShipFromCell,
                    getShipCells = getShipCells,
                    isShipInBounds = isShipInBounds,
                    updateCellInBound = updateCellInBound,
                    placeShip = placeShip,
                    onCellSizeCalculated = { cellSize = it },
                    divider = 2.5f
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DraggableShipView(
                        shipTypes = shipTypes,
                        startDragging = { ship, orientation -> startDragging(orientation, ship) },
                        stopDragging = { stopDragging() }
                    )

                    ButtonRow(randomizeShips, resetShips)
                }
            }
        }
    }
}

@Composable
private fun BoardSection(
    board: Array<Array<Cell>>,
    cellSize: Dp,
    getCellInBound: () -> GridCoordinates?,
    getShipFromCell: (x: Int, y: Int) -> PlacedShip?,
    getShipCells: (gridCoordinates: GridCoordinates) -> Map<GridCoordinates, Cell>,
    isShipInBounds: (gridCoordinates: GridCoordinates) -> Boolean,
    updateCellInBound: (GridCoordinates) -> Unit,
    placeShip: (ship: Ship, shipIndex: Int, x: Int, y: Int) -> Unit,
    onCellSizeCalculated: (Dp) -> Unit,
    divider: Float
) {
    BoxWithConstraints {
        val boardSize: Dp = maxWidth / divider

        PlayerGridDropZone(
            board = board,
            boardSize = boardSize,
            cellSize = cellSize,
            cellInBound = getCellInBound(),
            getShipFromCell = { x, y -> getShipFromCell(x, y) },
            getShipCells = { gridCoordinates -> getShipCells(gridCoordinates) },
            isShipInBounds = { coordinates -> isShipInBounds(coordinates) },
            updateCellInBound = { gridCoordinates ->
                updateCellInBound(gridCoordinates)
            },
            placeShip = { ship, shipIndex, x, y ->
                placeShip(ship, shipIndex, x, y)
            },
            onGloballyPositioned = { onCellSizeCalculated(it) },
            debug = true
        )
    }
}

@Composable
private fun ButtonRow(randomizeOnClick: () -> Unit, resetOnClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PixelButton(
            onClick = randomizeOnClick,
            cornerSize = 2,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Shuffle, contentDescription = null, tint = OnPixelButtonTextColor)
                Spacer(modifier = Modifier.padding(horizontal = 2.5.dp))
                Text(
                    text = stringResource(Res.string.randomizeBoard),
                    color = OnPixelButtonTextColor
                )
            }
        }

        PixelButton(
            onClick = resetOnClick,
            cornerSize = 2,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(ResetAlt, contentDescription = null, tint = OnPixelButtonTextColor)
                Spacer(modifier = Modifier.padding(horizontal = 2.5.dp))
                Text(text = stringResource(Res.string.resetBoard), color = OnPixelButtonTextColor)
            }
        }
    }
}