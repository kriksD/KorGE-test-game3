package inventory.weapons

import kotlinx.serialization.*

@Serializable
@SerialName("nothing")
data class NoWeapon(override var count: Int = 1) : WeaponItem {
    override val name: String = "nothing"
    override val damage: Int = 1
    override val priceForOne: Float = 0F
}
