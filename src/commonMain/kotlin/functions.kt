import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.tiled.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.tiles.tiled.*
import com.soywiz.korio.async.*
import com.soywiz.korio.lang.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.geom.collider.HitTestable
import inventory.*
import mobs.*
import kotlin.random.*

fun Stage.controlWithKeyboard(
    char: View,
    collider: HitTestable,
    up: Key = Key.UP,
    right: Key = Key.RIGHT,
    down: Key = Key.DOWN,
    left: Key = Key.LEFT,
) {
    addUpdater { dt ->
        val speed = 2.0 * (dt / 16.0.milliseconds)
        var dx = 0.0
        var dy = 0.0
        val pressingLeft = keys[left]
        val pressingRight = keys[right]
        val pressingUp = keys[up]
        val pressingDown = keys[down]
        if (pressingLeft) dx = -1.0
        if (pressingRight) dx = +1.0
        if (pressingUp) dy = -1.0
        if (pressingDown) dy = +1.0
        if (dx != 0.0 || dy != 0.0) {
            val dPos = Point(dx, dy).normalized * speed
            char.moveWithHitTestable(collider, dPos.x, dPos.y)
        }
    }
}

fun Stage.addHealing(playerMob: Player) {
    addFixedUpdater(2.seconds) {
        playerMob.recover()
    }
}

fun Stage.addDefeatChecker(
    playerMob: Player,
    playerView: View,
    spawnPosition: Point,
    inventory: Inventory,
) {
    addUpdater {
        if (playerMob.health <= 0) {
            inventory.defeat()
            playerView.position(spawnPosition)
            playerMob.health = playerMob.maxHealth
        }
    }
}

fun Stage.addMobAI(
    char: View,
    collider: HitTestable,
    player: View,
    mob: Mob,
    playerMob: Player,
    itemLayer: Container,
    mobObject: TiledMap.Object?,
    viewsManager: ViewsManager,
) {
    var isDead = false
    val updater = addUpdater { dt ->
        if (char.distanceTo(player.pos) <= 100) {
            val speed = 1.5 * (dt / 16.0.milliseconds)
            val dx = getStepSize(char.x, player.x, speed)
            val dy = getStepSize(char.y, player.y, speed)
            if (dx != 0.0 || dy != 0.0) {
                val dPos = Point(dx, dy).normalized * speed
                char.moveWithHitTestable(collider, dPos.x, dPos.y)
            }
        }

        if (char.distanceTo(player.pos) <= 20) {
            mob.attack(playerMob)
            isDead = playerMob.attack(mob)
        }
    }

    addUpdater {
        if (isDead) {
            /*Textures[mob.dropItem.name]?.let {
                itemLayer.image(it, 0.5, 0.5).position(char.pos).name(mob.dropItem.name)
            }*/

            viewsManager.getNewItem(mob.dropItem.name)?.view?.let {
                it.position(char.pos)
                itemLayer.addChild(it)
            }

            viewsManager.removeMob(mob)
            mobObject?.minusCount()
            updater.cancel()
            isDead = false
        }
    }
}

fun Stage.addSpawners(
    tileMapView: TiledMapView,
    charactersLayer: Container,
    playerMob: Player,
    playerView: View,
    viewsManager: ViewsManager,
) {
    addFixedUpdater(5.seconds) {
        tileMapView.tiledMap.data.getZones("item")?.forEach { obj ->
            val name = obj.properties["item"]?.string
            val count = obj.properties["count"]

            count?.let { itemCount ->
                if (itemCount.int < 10) {
                    val pos = obj.bounds.getRandomPosition()

                    val itemsLayer = tileMapView["inventory"].first as Container
                    name?.let { n ->
                        viewsManager.getNewItem(n)?.view?.let {
                            it.position(pos)
                            itemsLayer.addChild(it)

                            obj.plusCount()
                        }
                    }
                }
            }
        }
    }

    addFixedUpdater(10.seconds) {
        tileMapView.tiledMap.data.getZones("mob")?.forEach { obj ->
            val name = obj.properties["mob"]?.string
            val count = obj.properties["count"]

            count?.let { mobCount ->
                name?.let { mobName ->
                    if (mobCount.int < 5) {
                        val pos = obj.bounds.getRandomPosition()
                        val mob = viewsManager.getNewMob(mobName)

                        mob?.let { m ->
                            m.view?.let { mobView ->
                                mobView.position(pos)
                                charactersLayer.addChild(mobView)

                                addMobAI(
                                    mobView,
                                    tileMapView,
                                    playerView,
                                    m,
                                    playerMob,
                                    tileMapView["inventory"].first as Container,
                                    obj,
                                    viewsManager,
                                )

                                addHPBar(mobView, m, tileMapView["inventory"].first as Container)

                                obj.plusCount()
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Stage.addHPBar(
    view: View,
    mob: Mob,
    characterLayer: Container,
) {
    val hpBackground = characterLayer.solidRect(mob.maxHealth / 3, 2, Colors.RED).anchor(0.5, 0.5)
    val hpBar = characterLayer.solidRect(mob.health / 3, 2, Colors.GREEN)//.anchor(0.5, 0.5)

    val updater = addUpdater {
        hpBackground.hpPositionOf(view)
        hpBar.position(getCenterForHPBar(hpBackground, hpBar))

        hpBar.size(mob.health / 3, 2)
    }

    addUpdater {
        if (mob.health <= 0) {
            hpBar.removeFromParent()
            hpBackground.removeFromParent()

            updater.cancel()
        }
    }
}

fun View.hpPositionOf(view: View) {
    position(
        view.x,
        view.y - view.height - 2
    )
}

fun getCenterForHPBar(background: View, bar: View): Point {
    return Point(
        background.x - background.width / 2,
        background.y - background.height / 2,
    )
}

fun getStepSize(previous: Double, destination: Double, speed: Double): Double {
    return if (destination > previous && destination - previous > speed) {
        speed
    } else if (destination < previous && previous - destination > speed) {
        -speed
    } else {
        0.0
    }
}

fun Container.imageTextRow(image: Bitmap, text: String): Container {
    return container {
        roundRect(36.0, 36.0, 6.0, fill = RGBA(0, 0, 0, 100))
        val imageView = image(image).position(2, 2)
        text(text).alignLeftToRightOf(imageView, 6.0).centerYOn(imageView)
    }
}

fun Rectangle.getRandomPosition(): IPoint {
    return IPoint(
        Random(DateTime.now().milliseconds).nextInt(x.toInt(), (x + width).toInt()),
        Random(DateTime.now().milliseconds).nextInt(y.toInt(), (y + height).toInt())
    )
}

fun TiledMapData.getObjectsWithFilter(layer: String? = null, filter: ((obj: TiledMap.Object) -> Boolean) = { true }): List<TiledMap.Object>? {
    return if (layer == null) {
        val allObjects = mutableListOf<TiledMap.Object>()
        objectLayers.forEach {
            allObjects.addAll(it.objects)
        }
        allObjects.filter { filter(it) }

    } else {
        val invObjects = objectLayers.find { it.name == layer }?.objects
        invObjects?.filter { filter(it) }
    }
}

fun TiledMapData.getZones(zoneName: String): List<TiledMap.Object>? {
    return getObjectsWithFilter { it.properties["zone"]?.string == zoneName }
}

fun TiledMap.Object.plusCount(amount: Int = 1) {
    val count = properties["count"]?.int
    count?.let { c -> properties["count"] = TiledMap.Property.IntT(c + amount) }
}

fun TiledMap.Object.minusCount(amount: Int = 1) {
    val count = properties["count"]?.int
    count?.let { c -> properties["count"] = TiledMap.Property.IntT(c - amount) }
}
