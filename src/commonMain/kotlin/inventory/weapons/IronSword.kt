package inventory.weapons

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("ironSword")
data class IronSword(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : WeaponItem {
    override val name: String = "iron sword"
    override val damage: Int = 4
    override val priceForOne: Float = 300F
}
