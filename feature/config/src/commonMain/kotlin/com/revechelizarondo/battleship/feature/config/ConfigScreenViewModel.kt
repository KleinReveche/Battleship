package com.revechelizarondo.battleship.feature.config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.revechelizarondo.battleship.core.domain.models.BattleshipClassic
import com.revechelizarondo.battleship.core.domain.models.BattleshipHackerEdition
import com.revechelizarondo.battleship.core.domain.models.PlayerType
import com.revechelizarondo.battleship.core.domain.models.Ship

class ConfigScreenViewModel : ViewModel() {
    var showGameOptions by mutableStateOf(true)
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
        val map = if (selectedSkinIndex == 0) {
            BattleshipClassic.defaultsMap()
        } else {
            BattleshipHackerEdition.defaultsMap()
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

}