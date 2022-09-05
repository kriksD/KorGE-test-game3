package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("bone")
data class Bone(override var count: Int = 1) : SimpleItem {
    override val name: String = "bone"
    override val priceForOne: Float = 10F
}
