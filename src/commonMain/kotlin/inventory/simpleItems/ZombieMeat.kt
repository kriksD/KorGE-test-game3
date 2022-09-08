package inventory.simpleItems

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("zombieMeat")
data class ZombieMeat(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : SimpleItem {
    override val name: String = "zombie meat"
    override val priceForOne: Float = 5F
}
