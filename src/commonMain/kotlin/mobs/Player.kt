package mobs

import inventory.*
import inventory.simpleItems.*

class Player(
    override val name: String,
    override var damage: Int,
    override var health: Int = 100,
    override var maxHealth: Int = 100,
) : Mob {
    override val dropItem: InventoryItem = Stick(0)
    override var attackTimer: Int = 40

}
