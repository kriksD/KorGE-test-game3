package inventory.weapons

import kotlinx.serialization.*

@Serializable
@SerialName("ironSword")
data class IronSword(override var count: Int = 1) : WeaponItem {
    override val name: String = "iron sword"
    override val damage: Int = 4
    override val priceForOne: Float = 300F
}
