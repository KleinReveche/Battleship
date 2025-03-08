package com.revechelizarondo.battleship.core.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class BattleshipGameSettings(
    val gameType: GameType,
    val playMode: PlayMode,
    val player1Name: String,
    val player2Name: String,
    val difficulty: ComputerDifficulty,
    val gridSize: Int = 10,
    val boardLayoutTimeSeconds: Int = 50,
    val shotsPerTurn: Int = 1,
    val timePerTurnSeconds: Int = 100,
    val shipTypes: Map<Ship, Int>,
)