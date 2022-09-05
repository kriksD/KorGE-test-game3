package offers

import inventory.*
import inventory.weapons.*

data class WeaponOffer(
    val weapon: WeaponItem,
) : Offer {
    override fun completeOfferIfDone(inventory: Inventory): Boolean {
        return if (inventory.spendGold(weapon.getFullPrice().toInt())) {
            inventory.weapon = weapon
            true

        } else false
    }

    override fun toString(): String {
        return "I sell ${weapon.name} for ${weapon.getFullPrice().toInt()} gold."
    }

    companion object {
        fun getNextWeapon(previous: WeaponItem): WeaponItem {
            return when (previous) {
                is NoWeapon -> WoodenSword()
                is WoodenSword -> StoneSword()
                is StoneSword -> IronSword()
                is IronSword -> LegendarySword()
                is LegendarySword -> DragonSword()
                else -> NoWeapon()
            }
        }
    }
}
