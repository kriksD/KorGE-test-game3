package inventory.simpleItems

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("cactus")
data class Cactus(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : SimpleItem {
    override val name: String = "cactus"
    override val priceForOne: Float = 0.5F
}
