package mobs

import inventory.*
import inventory.simpleItems.*

class Zombie(
    override val name: String = "zombie",
    override var damage: Int = 4,
    override var health: Int = 30,
    override var maxHealth: Int = 30,
) : Mob {
    override val dropItem: InventoryItem = ZombieMeat(1)
    override var attackTimer: Int = 40
}
