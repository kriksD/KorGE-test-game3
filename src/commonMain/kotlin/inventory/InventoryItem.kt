package inventory

import inventory.simpleItems.*
import inventory.weapons.*
import kotlinx.serialization.*
import kotlin.math.*

interface InventoryItem {
    val name: String
    var count: Int
    val priceForOne: Float

    fun plus(count: Int = 1) {
        this.count += count
    }

    fun minus(count: Int = 1) {
        this.count = max(this.count - count, 0)
    }

    fun getFullPrice(): Float {
        return count * priceForOne
    }

    companion object {
        fun itemByName(name: String): InventoryItem? {
            return when (name.lowercase()) {
                "stick" -> Stick()
                "log" -> Log()
                "flower" -> Flower()
                "cactus" -> Cactus()
                "bone" -> Bone()
                "zombie meat" -> ZombieMeat()
                "wolf skin" -> WolfSkin()
                "nothing" -> NoWeapon()
                "wooden sword" -> WoodenSword()
                "stone sword" -> StoneSword()
                "iron sword" -> IronSword()
                "legendary sword" -> LegendarySword()
                "dragon sword" -> DragonSword()
                else -> null
            }
        }
    }
}
