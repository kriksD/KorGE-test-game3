package inventory.weapons

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("legendarySword")
data class LegendarySword(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : WeaponItem {
    override val name: String = "legendary sword"
    override val damage: Int = 6
    override val priceForOne: Float = 600F
}
