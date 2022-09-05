package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("zombieMeat")
data class ZombieMeat(override var count: Int = 1) : SimpleItem {
    override val name: String = "zombie meat"
    override val priceForOne: Float = 5F
}
