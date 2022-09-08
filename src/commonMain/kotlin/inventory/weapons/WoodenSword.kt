package inventory.weapons

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("woodenSword")
data class WoodenSword(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : WeaponItem {
    override val name: String = "wooden sword"
    override val damage: Int = 2
    override val priceForOne: Float = 40F
}
