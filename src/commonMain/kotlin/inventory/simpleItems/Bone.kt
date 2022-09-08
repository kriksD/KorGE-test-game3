package inventory.simpleItems

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("bone")
data class Bone(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : SimpleItem {
    override val name: String = "bone"
    override val priceForOne: Float = 10F
}
