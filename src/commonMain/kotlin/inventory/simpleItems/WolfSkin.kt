package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("wolfSkin")
data class WolfSkin(override var count: Int = 1) : SimpleItem {
    override val name: String = "wolf skin"
    override val priceForOne: Float = 3F
}
