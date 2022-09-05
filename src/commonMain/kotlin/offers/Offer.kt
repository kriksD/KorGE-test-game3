package offers

import inventory.*
import inventory.weapons.*

interface Offer {
    fun completeOfferIfDone(inventory: Inventory): Boolean

    companion object {
        fun makeOfferByName(traderName: String, weapon: WeaponItem): Offer? {
            return if (traderName != "trader") {
                val newItem = BuyOffer.getItem(traderName)
                BuyOffer(traderName, newItem)

            } else if (traderName == "trader") {
                val newWeapon = WeaponOffer.getNextWeapon(weapon)
                WeaponOffer(newWeapon)
            } else null
        }
    }
}
