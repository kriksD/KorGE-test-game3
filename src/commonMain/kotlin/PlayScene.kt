import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.component.docking.*
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korim.tiles.tiled.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import inventory.*
import inventory.simpleItems.*
import inventory.weapons.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import mobs.*
import offers.*

class PlayScene : Scene() {
    private val module = SerializersModule {
        polymorphic(InventoryItem::class) {
            subclass(Bone::class)
            subclass(Cactus::class)
            subclass(Flower::class)
            subclass(Gold::class)
            subclass(Log::class)
            subclass(Stick::class)
            subclass(WolfSkin::class)
            subclass(ZombieMeat::class)

            subclass(DragonSword::class)
            subclass(IronSword::class)
            subclass(LegendarySword::class)
            subclass(NoWeapon::class)
            subclass(StoneSword::class)
            subclass(WoodenSword::class)
        }

        polymorphic(SimpleItem::class) {
            subclass(Bone::class)
            subclass(Cactus::class)
            subclass(Flower::class)
            subclass(Gold::class)
            subclass(Log::class)
            subclass(Stick::class)
            subclass(WolfSkin::class)
            subclass(ZombieMeat::class)
        }

        polymorphic(WeaponItem::class) {
            subclass(DragonSword::class)
            subclass(IronSword::class)
            subclass(LegendarySword::class)
            subclass(NoWeapon::class)
            subclass(StoneSword::class)
            subclass(WoodenSword::class)
        }
    }
    private val json = Json { serializersModule = module }

    private lateinit var tileMap: TiledMap
    private lateinit var playerView: View
    private val player: Player = Player("Player69", 1)
    private val characterNames = listOf("trader", "hunter", "forester", "flowerist")
    //private lateinit var textures: Map<String, Bitmap>
    private var playerInventory = Inventory()
    private val traders = mutableListOf<Trader>()
    private var loadedPosition: Point? = null
    private val viewsManager = ViewsManager()

    override suspend fun SContainer.sceneInit() {
        tileMap = resourcesVfs["maps/village.tmx"].readTiledMap()

        Textures.load()
        /*val list = mutableMapOf<String, Bitmap>()
        list["stick"] = resourcesVfs["textures/stick.png"].readBitmap()
        list["log"] = resourcesVfs["textures/log.png"].readBitmap()
        list["flower"] = resourcesVfs["textures/flower.png"].readBitmap()
        list["cactus"] = resourcesVfs["textures/cactus.png"].readBitmap()
        list["bone"] = resourcesVfs["textures/bone.png"].readBitmap()
        list["zombie meat"] = resourcesVfs["textures/zombie meat.png"].readBitmap()
        list["wolf skin"] = resourcesVfs["textures/wolf skin.png"].readBitmap()
        list["gold"] = resourcesVfs["textures/gold.png"].readBitmap()

        list["trader"] = resourcesVfs["textures/trader.png"].readBitmap()
        list["player"] = resourcesVfs["textures/player.png"].readBitmap()
        list["hunter"] = resourcesVfs["textures/hunter.png"].readBitmap()
        list["forester"] = resourcesVfs["textures/forester.png"].readBitmap()
        list["flowerist"] = resourcesVfs["textures/flowerist.png"].readBitmap()
        list["zombie"] = resourcesVfs["textures/zombie.png"].readBitmap()
        list["wolf"] = resourcesVfs["textures/wolf.png"].readBitmap()
        list["skeleton"] = resourcesVfs["textures/skeleton.png"].readBitmap()

        list["nothing"] = resourcesVfs["textures/nothing.png"].readBitmap()
        list["wooden sword"] = resourcesVfs["textures/wooden sword.png"].readBitmap()
        list["stone sword"] = resourcesVfs["textures/stone sword.png"].readBitmap()
        list["iron sword"] = resourcesVfs["textures/iron sword.png"].readBitmap()
        list["legendary sword"] = resourcesVfs["textures/legendary sword.png"].readBitmap()
        list["dragon sword"] = resourcesVfs["textures/dragon sword.png"].readBitmap()

        list["health"] = resourcesVfs["textures/health.png"].readBitmap()
        textures = list*/

        val file = rootLocalVfs["player.json"]
        if (file.exists()) {
            val data = json.decodeFromString<SaveData>(rootLocalVfs["player.json"].readString())
            playerInventory = data.inventory
            player.damage = playerInventory.weapon.damage
            player.health = data.health
            loadedPosition = Point(data.x, data.y)
        }
    }

    override suspend fun SContainer.sceneMain() {
        container {
            lateinit var tileMapView: TiledMapView

            val cameraContainer = cameraContainer(
                900.0, 900.0, clip = true,
                block = {
                    clampToBounds = true
                }
            ) {
                tileMapView = tiledMapView(tileMap, smoothing = false, showShapes = false)

                val charactersLayer = tileMapView["characters"].first as Container
                val spawn = tileMapView.tiledMap.data.getObjectByName("spawn")

                val playerTexture = Textures["player"]
                playerView = if (playerTexture != null) {
                    charactersLayer.image(playerTexture)
                        .anchor(0.5, 1.0)
                        .position(spawn?.x ?: 200.0, spawn?.y ?: 200.0)
                } else {
                    charactersLayer.solidRect(20, 40, Colors.DARKBLUE)
                        .anchor(0.5, 1.0)
                        .position(spawn?.x ?: 200.0, spawn?.y ?: 200.0)
                }

                loadedPosition?.let { playerView.position(it) }

                stage?.addDefeatChecker(
                    player,
                    playerView,
                    Point(spawn?.x ?: 200.0, spawn?.y ?: 200.0),
                    playerInventory
                )

                playerView.onCollision({ viewsManager.isItemActive(it) }) { v ->
                    val item = viewsManager.findItemByView(v)
                    item?.let { ite ->
                        playerInventory.addItem(ite)
                        viewsManager.removeItem(ite)
                    }

                    tileMapView.tiledMap.data.getObjectsWithFilter("inventory") {
                        it.properties["zone"]?.string == "item" && it.properties["item"]?.string == v.name
                    }?.firstOrNull()?.minusCount()
                }

                val signs: MutableList<View> = mutableListOf()
                playerView.onCollision({ characterNames.contains(it.name) }) { char ->
                    if (signs.find { it.name == char.name } == null) {
                        val newSign = text(char.name ?: "unknown").position(char.pos).name(char.name)
                        signs.add(newSign)

                        traders.find { it.name == char.name }?.let { trader ->
                            trader.checkOffer(playerInventory)
                            player.damage = playerInventory.weapon.damage

                            val secondNewSign = text(trader.message, 13.0).position(char.x, char.y + 15).name(char.name)
                            signs.add(secondNewSign)
                        }
                    }
                }

                playerView.onCollisionExit({ characterNames.contains(it.name) }) { char ->
                    signs.filter { it.name == char.name }.forEach {
                        it.removeFromParent()
                        signs.remove(it)
                    }
                }

                addUpdater {
                    if (keys[Key.G]) {
                        playerInventory.addItem(Gold(10))
                    }
                }

                characterNames.forEach { charName ->
                    Textures[charName]?.let { charImage ->
                        val charSpawn = tileMapView.tiledMap.data.getObjectByName(charName)
                        charactersLayer.image(charImage)
                            .anchor(0.5, 1.0)
                            .position(charSpawn?.x ?: 200.0, charSpawn?.y ?: 200.0)
                            .name(charName)

                        traders.add(Trader(charName))
                    }
                }

                stage?.addSpawners(tileMapView, charactersLayer, player, playerView, viewsManager)

                charactersLayer.keepChildrenSortedByY()
            }

            cameraContainer.cameraViewportBounds.copyFrom(tileMapView.getLocalBoundsOptimized())

            stage?.controlWithKeyboard(playerView, tileMapView, Key.W, Key.D, Key.S, Key.A)
            stage?.addHealing(player)

            cameraContainer.follow(playerView, setImmediately = true)

            gameInterface()

            addFixedUpdater(3.seconds) {
                launchImmediately { saveData() }
            }
        }
    }

    private fun gameInterface(): Container {
        return stage.container {
            solidRect(104, 24, Colors.BLACK).position(18, 18)
            solidRect(100, 20, Colors.RED).position(20, 20)
            val hpBar = solidRect(player.health, 20, Colors.GREEN).position(20, 20)
            val hpText = text("").position(130, 20)

            addUpdater {
                hpBar.size(player.health, 20)
                hpText.text = "${player.health}/${player.maxHealth}"
            }

            val itemsContainer = container().position(20, 50)
            var previous: Container? = null

            addFixedUpdater(20.timesPerSecond) {
                itemsContainer.removeChildren()

                Textures[playerInventory.weapon.name]?.let { img ->
                    itemsContainer.imageTextRow(
                        img,
                        "${playerInventory.weapon.name}: damage: ${playerInventory.weapon.damage}",
                    )
                }

                playerInventory.items.forEach { item ->
                    Textures[item.name]?.let { img ->
                        val newItemText = itemsContainer.imageTextRow(img, "${item.name}: ${item.count}")

                        previous?.let { pre ->
                            newItemText.alignLeftToLeftOf(pre).alignTopToBottomOf(pre, 4.0)
                        }

                        previous = newItemText
                    }
                }
            }
        }
    }

    private suspend fun saveData() {
        val data = SaveData(
            playerInventory,
            player.health,
            playerView.x,
            playerView.y,
        )

        rootLocalVfs["player.json"].writeString(json.encodeToString(data))
    }
}
