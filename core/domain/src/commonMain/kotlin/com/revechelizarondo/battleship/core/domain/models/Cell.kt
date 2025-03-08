package com.revechelizarondo.battleship.core.domain.models

import kotlinx.serialization.Serializable

/**
 * A cell in the board.
 *
 * @property x the x coordinate of the cell
 * @property y the y coordinate of the cell
 * @property wasHit if the cell was hit
 * @property hitBy the identifier of the player who hit the cell
 */
@Serializable
sealed interface Cell {
    val x: Int
    val y: Int
    var wasHit: Boolean
    var hitBy: PlayerType?
}

/**
 * An empty cell.
 */
@Serializable
data class WaterCell(
    override val x: Int,
    override val y: Int,
    override var wasHit: Boolean,
    override var hitBy: PlayerType?
) : Cell

/**
 * A cell that contains a ship.
 *
 * @property shipIndex the index of the ship in the list of placed ships
 */
@Serializable
data class ShipCell(
    override val x: Int,
    override val y: Int,
    override var wasHit: Boolean,
    override var hitBy: PlayerType? = null,
    val shipIndex: Int,
) : Cell