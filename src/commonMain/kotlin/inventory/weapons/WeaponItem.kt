package inventory.weapons

import inventory.*

interface WeaponItem : InventoryItem {
    val damage: Int
}
