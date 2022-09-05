import inventory.*
import kotlinx.serialization.Serializable

@Serializable
data class SaveData(
    val inventory: Inventory,
    val health: Int,
    val x: Double,
    val y: Double,
)
