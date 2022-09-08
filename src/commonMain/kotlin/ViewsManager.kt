import com.soywiz.korge.view.*
import inventory.*
import mobs.*

class ViewsManager {
    private val itemViews = mutableMapOf<Boolean, MutableList<InventoryItem>>(
        Pair(true, mutableListOf()),
        Pair(false, mutableListOf())
    )
    private val mobViews = mutableMapOf<Boolean, MutableList<Mob>>(
        Pair(true, mutableListOf()),
        Pair(false, mutableListOf())
    )

    fun getNewItem(name: String): InventoryItem? {
        val item = itemViews[false]?.find { it.name == name }

        return if (item == null) {
            val view = Textures[name]?.let {
                Image(it, 0.5, 0.5).name(name)
            }

            val newItem = InventoryItem.itemByName(name, view)
            newItem?.let { itemViews[true]?.add(it) }
            newItem

        } else {
            itemViews[false]?.remove(item)
            itemViews[true]?.add(item)

            item
        }
    }

    fun getNewItem(sample: InventoryItem): InventoryItem {
        val item = itemViews[false]?.find { it.name == sample.name }

        return if (item == null) {
            val view = Textures[sample.name]?.let {
                Image(it, 0.5, 0.5).name(sample.name)
            }

            sample.view = view
            itemViews[true]?.add(sample)

            sample

        } else {
            itemViews[false]?.remove(item)
            itemViews[true]?.add(item)

            item.count = sample.count
            item
        }
    }

    fun removeItem(item: InventoryItem) {
        item.view?.removeFromParent()

        itemViews[false]?.add(item)
        itemViews[true]?.remove(item)
    }

    fun isItemActive(item: InventoryItem): Boolean {
        return itemViews[true]?.contains(item) == true
    }

    fun isItemActive(view: View): Boolean {
        return itemViews[true]?.find { it.view == view } != null
    }

    fun findItemByView(view: View): InventoryItem? {
        return itemViews[true]?.find { it.view == view }
    }

    fun getNewMob(name: String): Mob? {
        val mob = mobViews[false]?.find { it.name == name }

        return if (mob == null) {
            val view = Textures[name]?.let {
                Image(it).anchor(0.5, 1.0).name(name)
            }

            val newMob = Mob.mobByName(name, view)
            newMob?.let { mobViews[true]?.add(it) }
            newMob

        } else {
            mobViews[false]?.remove(mob)
            mobViews[true]?.add(mob)

            mob
        }
    }

    fun getNewMob(sample: Mob): Mob {
        val mob = mobViews[false]?.find { it.name == sample.name }

        return if (mob == null) {
            val view = Textures[sample.name]?.let {
                Image(it).anchor(0.5, 1.0).name(sample.name)
            }

            sample.view = view
            mobViews[true]?.add(sample)

            sample

        }  else {
            mobViews[false]?.remove(mob)
            mobViews[true]?.add(mob)

            mob
        }
    }

    fun removeMob(mob: Mob) {
        mob.view?.removeFromParent()

        mobViews[false]?.add(mob)
        mobViews[true]?.remove(mob)
    }

    fun isMobActive(item: Mob): Boolean {
        return mobViews[true]?.contains(item) == true
    }

    fun isMobActive(view: View): Boolean {
        return mobViews[true]?.find { it.view == view } != null
    }

    fun findMobByView(view: View): Mob? {
        return mobViews[true]?.find { it.view == view }
    }
}
