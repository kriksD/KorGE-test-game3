package inventory.weapons

import com.soywiz.korge.view.*
import kotlinx.serialization.*

@Serializable
@SerialName("nothing")
data class NoWeapon(
    override var count: Int = 1,
    @Transient  override var view: View? = null,
) : WeaponItem {
    override val name: String = "nothing"
    override val damage: Int = 1
    override val priceForOne: Float = 0F
}
