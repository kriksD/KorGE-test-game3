package inventory.weapons

import kotlinx.serialization.*

@Serializable
@SerialName("legendarySword")
data class LegendarySword(override var count: Int = 1) : WeaponItem {
    override val name: String = "legendary sword"
    override val damage: Int = 6
    override val priceForOne: Float = 600F
}
