package inventory.simpleItems

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("log")
data class Log(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : SimpleItem {
    override val name: String = "log"
    override val priceForOne: Float = 2F
}
