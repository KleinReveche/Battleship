package com.revechelizarondo.battleship.core.domain.engine

import com.revechelizarondo.battleship.core.domain.models.BattleshipBoard
import com.revechelizarondo.battleship.core.domain.models.BattleshipGameState
import com.revechelizarondo.battleship.core.domain.models.ComputerDifficulty
import com.revechelizarondo.battleship.core.domain.models.GridCoordinates
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.PlacedShip
import com.revechelizarondo.battleship.core.domain.models.PlayerType
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.domain.models.ShipCell
import com.revechelizarondo.battleship.core.domain.models.WaterCell
import kotlin.random.Random

object LocalVsComputerGameEngine {
    fun generateNewBoard(
        gridSize: Int,
        shipTypes: Map<Ship, Int>,
        owner: PlayerType,
        difficulty: ComputerDifficulty
    ): Pair<BattleshipBoard, List<PlacedShip>> {
        val board = BattleshipGameState.generateEmptyBoard(gridSize)
        val placedShips = mutableListOf<PlacedShip>()

        shipTypes.forEach { (ship, count) ->
            repeat(count) {
                var placed = false
                while (!placed) {
                    val orientation = Random.nextBoolean()
                    val startX = Random.nextInt(gridSize)
                    val startY = Random.nextInt(gridSize)

                    if (canPlaceShip(board, ship, startX, startY, orientation) &&
                        !isNearOtherShips(board, startX, startY, orientation, ship.size)
                    ) {
                        placeShip(board, placedShips, ship, startX, startY, orientation, owner)
                        placed = true
                    }
                }
            }
        }

        return board to placedShips
    }

    private fun canPlaceShip(
        board: BattleshipBoard,
        ship: Ship,
        startX: Int,
        startY: Int,
        vertical: Boolean
    ): Boolean {
        val length = ship.size
        return if (vertical) {
            startX + length <= board.size && (0 until length).all { board[startX + it][startY] is WaterCell }
        } else {
            startY + length <= board.size && (0 until length).all { board[startX][startY + it] is WaterCell }
        }
    }

    private fun placeShip(
        board: BattleshipBoard,
        placedShips: MutableList<PlacedShip>,
        ship: Ship,
        startX: Int,
        startY: Int,
        vertical: Boolean,
        owner: PlayerType
    ) {
        val length = ship.size
        val gridCoordinates = mutableListOf<GridCoordinates>()
        val orientation = if (vertical) Orientation.VERTICAL else Orientation.HORIZONTAL

        if (vertical) {
            (0 until length).forEach {
                gridCoordinates.add(GridCoordinates(startX + it, startY))
                board[startX + it][startY] =
                    ShipCell(startX + it, startY, false, null, placedShips.size)
            }
        } else {
            (0 until length).forEach {
                gridCoordinates.add(GridCoordinates(startX, startY + it))
                board[startX][startY + it] =
                    ShipCell(startX, startY + it, false, null, placedShips.size)
            }
        }

        placedShips.add(PlacedShip(ship, orientation, owner, gridCoordinates))
    }

    private fun isNearOtherShips(
        board: BattleshipBoard,
        startX: Int,
        startY: Int,
        vertical: Boolean,
        length: Int
    ): Boolean {
        val range = -1..1
        return if (vertical) {
            (0 until length).any { i ->
                range.any { dx ->
                    range.any { dy ->
                        isShipCell(
                            board,
                            startX + i + dx,
                            startY + dy
                        )
                    }
                }
            }
        } else {
            (0 until length).any { i ->
                range.any { dx ->
                    range.any { dy ->
                        isShipCell(
                            board,
                            startX + dx,
                            startY + i + dy
                        )
                    }
                }
            }
        }
    }

    private fun isShipCell(board: BattleshipBoard, x: Int, y: Int): Boolean {
        return x in board.indices && y in board.indices && board[x][y] is ShipCell
    }

}