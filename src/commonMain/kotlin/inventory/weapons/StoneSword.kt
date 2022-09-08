package inventory.weapons

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("stoneSword")
data class StoneSword(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : WeaponItem {
    override val name: String = "stone sword"
    override val damage: Int = 3
    override val priceForOne: Float = 100F
}
