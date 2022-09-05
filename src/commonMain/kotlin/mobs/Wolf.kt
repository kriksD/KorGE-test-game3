package mobs

import inventory.*
import inventory.simpleItems.*

class Wolf(
    override val name: String = "wolf",
    override var damage: Int = 2,
    override var health: Int = 15,
    override var maxHealth: Int = 15,
) : Mob {
    override val dropItem: InventoryItem = WolfSkin(1)
    override var attackTimer: Int = 40
}
