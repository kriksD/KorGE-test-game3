package mobs

import com.soywiz.korge.view.*
import inventory.*
import inventory.simpleItems.*

class Zombie(
    override val name: String = "zombie",
    override var damage: Int = 4,
    override var health: Int = 30,
    override var maxHealth: Int = 30,
    override var view: View? = null,
) : Mob {
    override val dropItem: InventoryItem = ZombieMeat(1)
    override var attackTimer: Int = 40
}
