package inventory.simpleItems

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("stick")
data class Stick(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : SimpleItem {
    override val name: String = "stick"
    override val priceForOne: Float = 1F
}
