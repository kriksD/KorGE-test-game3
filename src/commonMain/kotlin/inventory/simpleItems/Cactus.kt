package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("cactus")
data class Cactus(override var count: Int = 1) : SimpleItem {
    override val name: String = "cactus"
    override val priceForOne: Float = 0.5F
}
