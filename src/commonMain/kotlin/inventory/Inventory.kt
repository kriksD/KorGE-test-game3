package inventory

import inventory.simpleItems.*
import inventory.weapons.*
import kotlinx.serialization.Serializable
import kotlin.math.*

@Serializable
data class Inventory(
    val items: MutableList<InventoryItem> = mutableListOf(
        Gold(10),
    ),
    var weapon: WeaponItem = NoWeapon(1),
) {
    fun spendGold(amount: Int): Boolean {
        items.find { it is Gold }?.let { gold ->
            if (gold.count >= amount) {
                gold.minus(amount)
                return true
            }
        }

        return false
    }

    override fun toString(): String {
        return "$items  Weapon: $weapon"
    }

    fun addItem(item: InventoryItem) {
        val itemExist = items.find { it.name == item.name }
        if (itemExist != null) {
            itemExist.count += item.count
        } else {
            items.add(item)
        }
    }

    fun removeItem(item: InventoryItem) {
        val itemExist = items.find { it.name == item.name }
        if (itemExist != null) {
            itemExist.count = max(itemExist.count - item.count, 0)
        } else {
            item.count = 0
            items.add(item)
        }
    }

    fun clear() {
        items.forEach { item ->
            item.count = 0
        }

        weapon = NoWeapon()
    }

    fun defeat() {
        val saveWeapon = weapon
        clear()
        weapon = saveWeapon
    }
}
