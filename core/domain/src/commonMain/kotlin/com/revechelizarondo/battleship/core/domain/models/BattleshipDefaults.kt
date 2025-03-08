package com.revechelizarondo.battleship.core.domain.models

interface BattleshipDefaults {
    fun defaults(): List<Ship>
    fun defaultsMap(): Map<Ship, Int>
}

object ShipRegistry {
    private val ships = mutableMapOf<String, Ship>()

    fun init() {
        registerShips(BattleshipClassic.defaults())
        registerShips(BattleshipHackerEdition.defaults())
    }

    private fun registerShips(shipList: List<Ship>) {
        shipList.forEach { ship ->
            if (ships.containsKey(ship.shipName) || ships.containsKey(ship.shipCode))
                throw IllegalArgumentException("Ship with name ${ship.shipName} or code ${ship.shipCode} already exists.")

            ships[ship.shipName] = ship
        }
    }

    fun getShipByName(name: String): Ship? {
        return ships[name]
    }
}

object BattleshipClassic : BattleshipDefaults {
    val CARRIER = Ship(
        size = 5,
        shipName = "Carrier",
        shipCode = "CAR",
        shape = arrayOf(arrayOf(1, 1, 1, 1, 1))
    )
    val BATTLESHIP = Ship(
        size = 4,
        shipName = "Battleship",
        shipCode = "BAT",
        shape = arrayOf(arrayOf(1, 1, 1, 1))
    )
    val CRUISER =
        Ship(size = 3, shipName = "Cruiser", shipCode = "CRU", shape = arrayOf(arrayOf(1, 1, 1)))
    val SUBMARINE =
        Ship(size = 3, shipName = "Submarine", shipCode = "SUB", shape = arrayOf(arrayOf(1, 1, 1)))
    val DESTROYER =
        Ship(size = 2, shipName = "Destroyer", shipCode = "DES", shape = arrayOf(arrayOf(1, 1)))

    override fun defaults(): List<Ship> = listOf(CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER)
    override fun defaultsMap(): Map<Ship, Int> = defaults().associateWith { 1 }
}

object BattleshipHackerEdition : BattleshipDefaults {
    val MONITOR = Ship(
        size = 7,
        shipName = "Monitor",
        shipCode = "MON",
        shape = arrayOf(
            arrayOf(1, 1, 1),
            arrayOf(1, 1, 1),
            arrayOf(0, 1, 0)
        )
    )
    val SYSTEM_UNIT = Ship(
        size = 6,
        shipName = "System Unit",
        shipCode = "SYS",
        shape = arrayOf(
            arrayOf(1, 1),
            arrayOf(1, 1),
            arrayOf(1, 1)
        )
    )
    val KEYBOARD = Ship(
        size = 3,
        shipName = "Keyboard",
        shipCode = "KEY",
        shape = arrayOf(
            arrayOf(1, 1, 1)
        )
    )
    val MOUSE = Ship(
        size = 2,
        shipName = "Mouse",
        shipCode = "MOU",
        shape = arrayOf(
            arrayOf(1, 1)
        )
    )
    val ROUTER = Ship(
        size = 5,
        shipName = "Router",
        shipCode = "ROU",
        shape = arrayOf(
            arrayOf(1, 0, 1),
            arrayOf(1, 1, 1)
        )
    )

    override fun defaults(): List<Ship> = listOf(MONITOR, SYSTEM_UNIT, KEYBOARD, MOUSE, ROUTER)

    override fun defaultsMap(): Map<Ship, Int> = defaults().associateWith { 1 }
}