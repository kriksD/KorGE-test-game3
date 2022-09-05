package inventory.weapons

import kotlinx.serialization.*

@Serializable
@SerialName("dragonSword")
data class DragonSword(override var count: Int = 1) : WeaponItem {
    override val name: String = "dragon sword"
    override val damage: Int = 10
    override val priceForOne: Float = 1500F
}
