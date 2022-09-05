package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("stick")
data class Stick(override var count: Int = 1) : SimpleItem {
    override val name: String = "stick"
    override val priceForOne: Float = 1F
}
