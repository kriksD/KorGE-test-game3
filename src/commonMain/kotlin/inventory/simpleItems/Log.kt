package inventory.simpleItems

import kotlinx.serialization.*

@Serializable
@SerialName("log")
data class Log(override var count: Int = 1) : SimpleItem {
    override val name: String = "log"
    override val priceForOne: Float = 2F
}
