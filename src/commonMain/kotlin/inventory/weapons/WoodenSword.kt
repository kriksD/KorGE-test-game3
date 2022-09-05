package inventory.weapons

import kotlinx.serialization.*

@Serializable
@SerialName("woodenSword")
data class WoodenSword(override var count: Int = 1) : WeaponItem {
    override val name: String = "wooden sword"
    override val damage: Int = 2
    override val priceForOne: Float = 40F
}
