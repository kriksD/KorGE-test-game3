package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("flower")
data class Flower(override var count: Int = 1) : SimpleItem {
    override val name: String = "flower"
    override val priceForOne: Float = 0.5F
}
