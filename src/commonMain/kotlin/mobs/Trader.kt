package mobs

import inventory.*
import inventory.simpleItems.*
import offers.*

class Trader(override val name: String) : Mob {
    override val dropItem: InventoryItem = Stick(0)
    override var damage: Int = 0
    override var health: Int = 10
    override var maxHealth: Int = 10
    override var attackTimer: Int = 30

    private var offer: Offer? = null

    var message: String = ""

    fun checkOffer(inventory: Inventory): Boolean {
        if (offer == null) {
            offer = Offer.makeOfferByName(name, inventory.weapon)
        }

        message = offer.toString()

        return if (offer?.completeOfferIfDone(inventory) == true) {
            offer = null
            message = "Thanks!"
            true
        } else false
    }
}
