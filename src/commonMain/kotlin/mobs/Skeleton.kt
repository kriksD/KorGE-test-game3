package mobs

import com.soywiz.korge.view.*
import inventory.*
import inventory.simpleItems.*

class Skeleton(
    override val name: String = "skeleton",
    override var damage: Int = 5,
    override var health: Int = 50,
    override var maxHealth: Int = 50,
    override var view: View? = null,
) : Mob {
    override val dropItem: InventoryItem = Bone(1)
    override var attackTimer: Int = 40
}
