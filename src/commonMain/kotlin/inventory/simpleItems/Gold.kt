package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("gold")
data class Gold(override var count: Int = 1) : SimpleItem {
    override val name: String = "gold"
    override val priceForOne: Float = 1F
}
