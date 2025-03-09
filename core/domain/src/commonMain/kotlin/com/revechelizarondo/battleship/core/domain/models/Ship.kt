package com.revechelizarondo.battleship.core.domain.models

import com.revechelizarondo.battleship.core.domain.BattleshipParcelable
import com.revechelizarondo.battleship.core.domain.BattleshipParcelize
import kotlinx.serialization.Serializable

/**
 * The ship class in the game.
 *
 * @property size the size of the ship
 * @property shipName the name of the ship
 * @property shipCode the 3-letter code of the ship
 * @property shape the shape of the ship using a 2D list where 1 represents a point in the ship
 * @property isClassicShape if the ship is a classic shape where the ship is a straight line
 */
@BattleshipParcelize
@Serializable
data class Ship(
    val size: Int,
    val shipName: String,
    val shipCode: String,
    val shape: Array<Array<Int>>
) : BattleshipParcelable {
    init {
        val points = shape.sumOf { it.sum() }
        require(points == size) { "The number of points in the ship must be equal to the size of the ship." }
    }

    fun isClassicShape(): Boolean {
        return this.shape.size == 1
    }

    /**
     * Calculates the size of the ship in pixels.
     *
     * @param cellSize the size of a single cell in pixels
     * @return a pair containing the width and height of the ship in pixels
     */
    fun calculateSize(cellSize: Int): Pair<Int, Int> {
        val width = shape[0].size * cellSize
        val height = shape.size * cellSize
        return Pair(width, height)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Ship

        if (size != other.size) return false
        if (shipName != other.shipName) return false
        if (shipCode != other.shipCode) return false
        if (!shape.contentDeepEquals(other.shape)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size
        result = 31 * result + shipName.hashCode()
        result = 31 * result + shipCode.hashCode()
        result = 31 * result + shape.contentDeepHashCode()
        return result
    }
}

/**
 * A placed ship in the game.
 *
 * @property ship the ship
 * @property orientation the orientation of the ship
 * @property owner the owner of the ship
 * @property shipCells the list of coordinates of the ship cells
 */
@Serializable
data class PlacedShip(
    val ship: Ship,
    val orientation: Orientation,
    val owner: PlayerType,
    val shipCells: List<GridCoordinates>,
    var hitCount: Int = 0
) {
    fun isSunk(): Boolean = hitCount == ship.size
}

/**
 * The coordinates of a ship cell.
 *
 * @property x the x coordinate of the cell
 * @property y the y coordinate of the cell
 */
@Serializable
data class GridCoordinates(
    val x: Int,
    val y: Int
)