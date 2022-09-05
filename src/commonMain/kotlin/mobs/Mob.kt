package mobs

import inventory.*
import inventory.simpleItems.*
import inventory.weapons.*
import kotlin.math.*

interface Mob {
    val dropItem: InventoryItem
    val name: String
    var damage: Int
    var health: Int
    var maxHealth: Int
    var attackTimer: Int

    fun attack(mob: Mob): Boolean {
        return if (attackTimer <= 0) {
            attackTimer = 40
            mob.takeDamage(this)
        } else {
            attackTimer--
            false
        }
    }

    fun takeDamage(mob: Mob): Boolean {
        health = max(0, health - mob.damage)
        return health <= 0
    }

    fun recover(healthCount: Int = 1) {
        health = min(maxHealth, health + healthCount)
    }

    companion object {
        fun mobByName(name: String): Mob? {
            return when (name.lowercase()) {
                "zombie" -> Zombie()
                "skeleton" -> Skeleton()
                "wolf" -> Wolf()
                else -> null
            }
        }
    }
}
