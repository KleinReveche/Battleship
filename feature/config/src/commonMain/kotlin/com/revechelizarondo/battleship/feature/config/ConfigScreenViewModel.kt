package com.revechelizarondo.battleship.feature.config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.revechelizarondo.battleship.core.domain.engine.LocalVsComputerGameEngine
import com.revechelizarondo.battleship.core.domain.models.BattleshipClassic
import com.revechelizarondo.battleship.core.domain.models.BattleshipGameState
import com.revechelizarondo.battleship.core.domain.models.BattleshipHackerEdition
import com.revechelizarondo.battleship.core.domain.models.Cell
import com.revechelizarondo.battleship.core.domain.models.ComputerDifficulty
import com.revechelizarondo.battleship.core.domain.models.GridCoordinates
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.PlacedShip
import com.revechelizarondo.battleship.core.domain.models.PlayerType
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.domain.models.ShipCell

class ConfigScreenViewModel : ViewModel() {
    private var _cellInBound: GridCoordinates? by mutableStateOf(null)
    private var _currentOrientation: Orientation by mutableStateOf(Orientation.VERTICAL)
    private var _currentShip: Ship? by mutableStateOf(null)
    private var _isCurrentlyDragging by mutableStateOf(false)
    
    var showGameOptions by mutableStateOf(true)
        private set
    var selectedPlayIndex by mutableIntStateOf(0)
    var selectedSkinIndex by mutableIntStateOf(0)
    var player1Name by mutableStateOf("Player 1")
    var player2Name by mutableStateOf("Player 2")
    var gridSize by mutableIntStateOf(10)
    var boardLayoutTimeSeconds by mutableIntStateOf(50)
    var shotsPerTurn by mutableIntStateOf(1)
    var timePerTurnSeconds by mutableIntStateOf(100)
    var shipTypes = mutableStateMapOf<Ship, Int>()
    var playingPlayer by mutableStateOf(PlayerType.PLAYER_1)
    var activeShipTypes = mutableStateMapOf<Ship, Int>()
    var activeBoard by mutableStateOf(BattleshipGameState.generateEmptyBoard(gridSize))
    var placedShips = mutableStateMapOf<PlayerType, List<PlacedShip>>(
        PlayerType.PLAYER_1 to emptyList(),
        PlayerType.PLAYER_2 to emptyList()
    )

    init {
        populateShipTypes()
    }

    fun validateInput(): Boolean {
        return player1Name.isNotEmpty() && (selectedPlayIndex != 1 || player2Name.isNotEmpty())
    }

    fun resetGameSettings() {
        gridSize = 10
        boardLayoutTimeSeconds = 50
        shotsPerTurn = 1
        timePerTurnSeconds = 100
        playingPlayer = PlayerType.PLAYER_1
        shipTypes = shipTypes.apply {
            shipTypes.keys.forEach { ship ->
                this[ship] = 1
            }
        }

    }

    fun populateShipTypes() {
        val map = when (selectedSkinIndex) {
            1 -> BattleshipHackerEdition.defaultsMap()
            else -> BattleshipClassic.defaultsMap()
        }

        shipTypes = mutableStateMapOf()
        shipTypes.putAll(map)
    }

    fun addShip(ship: Ship) {
        shipTypes = shipTypes.apply {
            this[ship] = (this[ship] ?: 0) + 1
        }
    }

    fun removeShip(ship: Ship) {
        shipTypes = shipTypes.apply {
            this[ship] = (this[ship] ?: 0) - 1
        }
    }

    /* Player Ship Config */

    fun startPlayerShipConfig() {
        val map = when (selectedSkinIndex) {
            1 -> BattleshipHackerEdition.defaultsMap()
            else -> BattleshipClassic.defaultsMap()
        }

        showGameOptions = false
        activeBoard = BattleshipGameState.generateEmptyBoard(gridSize)
        activeShipTypes = mutableStateMapOf()
        activeShipTypes.putAll(map)


    }

    fun startDragging(orientation: Orientation, ship: Ship) {
        _isCurrentlyDragging = true
        _currentOrientation = orientation
        _currentShip = ship
    }

    fun stopDragging() {
        _isCurrentlyDragging = false
        _cellInBound = null
    }

    fun updateCellInBound(gridCoordinates: GridCoordinates) {
        _cellInBound = gridCoordinates
    }

    fun isShipInBounds(coordinates: GridCoordinates): Boolean {
        val coords = getShipCells(coordinates).keys
        return !coords.any { it.x < 0 || it.y < 0 || it.x >= gridSize || it.y >= gridSize } && coords.isNotEmpty()
    }

    fun getShipCells(coordinates: GridCoordinates): Map<GridCoordinates, Cell> {
        val (x, y) = coordinates
        val shipType = _currentShip ?: return emptyMap()
        val orientation = _currentOrientation
        val shipCoords = mutableMapOf<GridCoordinates, Cell>()

        val coords = generateShipCoordinates(x, y, shipType, orientation)

        for (coord in coords) {
            if (coord.x < 0 || coord.y < 0 || coord.x >= gridSize || coord.y >= gridSize) continue

            val cell = activeBoard[coord.x][coord.y]
            shipCoords[coord] = cell
        }

        return shipCoords
    }

    private fun generateShipCoordinates(
        x: Int,
        y: Int,
        shipType: Ship,
        orientation: Orientation
    ): List<GridCoordinates> {
        return if (shipType.isClassicShape()) {
            when (orientation) {
                Orientation.HORIZONTAL -> {
                    if ((y + shipType.size) <= gridSize) {
                        (y until y + shipType.size).map { GridCoordinates(x, it) }
                    } else {
                        emptyList()
                    }
                }

                Orientation.VERTICAL -> {
                    if ((x + shipType.size) <= gridSize) {
                        (x until x + shipType.size).map { GridCoordinates(it, y) }
                    } else {
                        emptyList()
                    }
                }
            }
        } else {
            val shipShape = shipType.shape
            val shapeCoords = mutableListOf<GridCoordinates>()
            for (i in shipShape.indices) {
                for (j in shipShape[i].indices) {
                    if (shipShape[i][j] == 1) {
                        val coord = when (orientation) {
                            Orientation.HORIZONTAL -> GridCoordinates(x + i, y + j)
                            Orientation.VERTICAL -> GridCoordinates(x + j, y + i)
                        }
                        shapeCoords.add(coord)
                    }
                }
            }
            shapeCoords
        }
    }

    fun getCellInBound(): GridCoordinates? = _cellInBound

    fun getShipFromCell(x: Int, y: Int): PlacedShip? {
        val cell = activeBoard[x][y]
        return if (cell is ShipCell) getShip(cell.shipIndex) else null
    }

    private fun getShip(shipIndex: Int): PlacedShip? {
        return placedShips[playingPlayer]?.get(shipIndex)
    }

    fun randomizeShips() {
        val newBoard = LocalVsComputerGameEngine.generateNewBoard(
            gridSize,
            shipTypes,
            playingPlayer,
            ComputerDifficulty.entries[selectedPlayIndex]
        )

        activeBoard = newBoard.first
        placedShips = placedShips.apply {
            this[playingPlayer] = newBoard.second.toMutableList()
        }

        activeShipTypes = activeShipTypes.apply {
            for (ship in this.keys) {
                this[ship] = 0
            }
        }
    }

    fun resetShips() {
        activeBoard = BattleshipGameState.generateEmptyBoard(gridSize)
        placedShips = placedShips.apply {
            this[playingPlayer] = mutableListOf()
        }

        activeShipTypes.clear()
        activeShipTypes.putAll(shipTypes)
    }

    fun placeShip(ship: Ship, shipIndex: Int, x: Int, y: Int) {
        val newBoard = activeBoard.copyOf()
        val coords = getShipCells(GridCoordinates(x, y)).keys.toList()

        // Don't place ship if any coordinate already contains a ship
        if (coords.any { newBoard[it.x][it.y] is ShipCell }) return

        val placedShip = PlacedShip(ship, _currentOrientation, playingPlayer, coords)

        coords.forEach {
            newBoard[it.x][it.y] = ShipCell(x, y, false, null, shipIndex)
        }

        activeShipTypes = activeShipTypes.apply {
            this[ship] = this[ship]!! - 1
        }

        activeBoard = newBoard
        placedShips = placedShips.apply {
            this[playingPlayer] = this[playingPlayer]?.toMutableList()?.apply {
                add(placedShip)
            } ?: mutableListOf(placedShip)
        }

        _currentShip = null
    }

    fun returnToGameParams() {
        resetShips()
        showGameOptions = true
    }
}