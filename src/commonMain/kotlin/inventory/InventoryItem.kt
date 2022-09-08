package inventory

import com.soywiz.korge.view.*
import inventory.simpleItems.*
import inventory.weapons.*
import kotlinx.serialization.*
import kotlin.math.*

interface InventoryItem {
    val name: String
    var count: Int
    val priceForOne: Float
    var view: View?

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
        fun itemByName(name: String, view: View? = null): InventoryItem? {
            return when (name.lowercase()) {
                "stick" -> Stick(view = view)
                "log" -> Log(view = view)
                "flower" -> Flower(view = view)
                "cactus" -> Cactus(view = view)
                "bone" -> Bone(view = view)
                "zombie meat" -> ZombieMeat(view = view)
                "wolf skin" -> WolfSkin(view = view)
                "nothing" -> NoWeapon(view = view)
                "wooden sword" -> WoodenSword(view = view)
                "stone sword" -> StoneSword(view = view)
                "iron sword" -> IronSword(view = view)
                "legendary sword" -> LegendarySword(view = view)
                "dragon sword" -> DragonSword(view = view)
                else -> null
            }
        }
    }
}
