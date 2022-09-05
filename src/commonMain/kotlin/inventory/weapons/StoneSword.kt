package inventory.weapons

import kotlinx.serialization.*

@Serializable
@SerialName("stoneSword")
data class StoneSword(override var count: Int = 1) : WeaponItem {
    override val name: String = "stone sword"
    override val damage: Int = 3
    override val priceForOne: Float = 100F
}
