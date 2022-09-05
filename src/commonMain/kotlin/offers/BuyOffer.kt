package offers

import inventory.*
import inventory.simpleItems.*
import kotlinx.serialization.Serializable

@Serializable
data class BuyOffer(
    val offeredBy: String,
    val item: InventoryItem,
): Offer {
    override fun toString(): String {
        return "Bring me ${item.count} ${item.name} and I'll give you ${item.getFullPrice().toInt()} gold."
    }

    override fun completeOfferIfDone(inventory: Inventory): Boolean {
        inventory.items.find { it.name == item.name }?.let { ii ->
            if (ii.count >= item.count) {
                inventory.removeItem(item)
                inventory.addItem(Gold(item.getFullPrice().toInt()))
                return true
            }
        }

        return false
    }

    companion object {
        fun getItem(characterName: String): InventoryItem {
            return when (characterName) {
                "hunter" -> listOf(WolfSkin((5..15).random()), ZombieMeat((3..10).random()), Bone((3..10).random())).random()
                "forester" -> listOf(Stick((20..40).random()), Log((10..30).random())).random()
                "flowerist" -> listOf(Flower((10..20).random()), Cactus((10..20).random())).random()
                else -> Stick(0)
            }
        }
    }
}
