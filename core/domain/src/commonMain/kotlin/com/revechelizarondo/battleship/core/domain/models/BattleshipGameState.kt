package com.revechelizarondo.battleship.core.domain.models

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

const val COMPUTER = "Computer"
const val EXTERNAL_PLAYER = "EP_"

typealias BattleshipBoard = Array<Array<Cell>>

@Serializable
data class BattleshipGameState @OptIn(ExperimentalUuidApi::class) constructor(
    val gameSettings: BattleshipGameSettings,
    var player1Board: BattleshipBoard = generateEmptyBoard(gameSettings.gridSize),
    var player2Board: BattleshipBoard = generateEmptyBoard(gameSettings.gridSize),
    val placedShips: MutableMap<PlayerType, List<PlacedShip>> = emptyPlayerShips(),
    var currentPlayerTurn: PlayerType = PlayerType.PLAYER_1,
    var winner: PlayerType? = null,
    val gameId: String = "${gameSettings.gameType.ordinal}${gameSettings.difficulty.ordinal}${gameSettings.playMode.ordinal}-${Uuid.random()}",
    var round: Int = 1,
    //var moveHistory: List<Move> = emptyList()
) {
    companion object {
        fun generateEmptyBoard(gridSize: Int): BattleshipBoard {
            return Array(gridSize) { y ->
                Array(gridSize) { x ->
                    WaterCell(x, y, false, null)
                }
            }
        }

        fun emptyPlayerShips(): MutableMap<PlayerType, List<PlacedShip>> {
            return mutableMapOf(
                PlayerType.PLAYER_1 to emptyList(),
                PlayerType.PLAYER_2 to emptyList()
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BattleshipGameState

        if (round != other.round) return false
        if (gameSettings != other.gameSettings) return false
        if (!player1Board.contentDeepEquals(other.player1Board)) return false
        if (!player2Board.contentDeepEquals(other.player2Board)) return false
        if (placedShips != other.placedShips) return false
        if (currentPlayerTurn != other.currentPlayerTurn) return false
        if (winner != other.winner) return false
        if (gameId != other.gameId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = round
        result = 31 * result + gameSettings.hashCode()
        result = 31 * result + player1Board.contentDeepHashCode()
        result = 31 * result + player2Board.contentDeepHashCode()
        result = 31 * result + placedShips.hashCode()
        result = 31 * result + currentPlayerTurn.hashCode()
        result = 31 * result + (winner?.hashCode() ?: 0)
        result = 31 * result + gameId.hashCode()
        return result
    }
}