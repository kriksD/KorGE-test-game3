import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.*
import com.soywiz.korio.file.std.*
import kotlinx.coroutines.flow.*

object Textures {
    private val textures: MutableMap<String, Bitmap> = mutableMapOf()

    suspend fun load() {
        resourcesVfs["textures"].list().toList().forEach {
            if (it.fullName.contains(Regex(".png$"))) {
                textures[it.baseNameWithoutExtension] = it.readBitmap()
            }
        }

        /*textures["stick"] = resourcesVfs["textures/stick.png"].readBitmap()
        textures["log"] = resourcesVfs["textures/log.png"].readBitmap()
        textures["flower"] = resourcesVfs["textures/flower.png"].readBitmap()
        textures["cactus"] = resourcesVfs["textures/cactus.png"].readBitmap()
        textures["bone"] = resourcesVfs["textures/bone.png"].readBitmap()
        textures["zombie meat"] = resourcesVfs["textures/zombie meat.png"].readBitmap()
        textures["wolf skin"] = resourcesVfs["textures/wolf skin.png"].readBitmap()
        textures["gold"] = resourcesVfs["textures/gold.png"].readBitmap()

        textures["trader"] = resourcesVfs["textures/trader.png"].readBitmap()
        textures["player"] = resourcesVfs["textures/player.png"].readBitmap()
        textures["hunter"] = resourcesVfs["textures/hunter.png"].readBitmap()
        textures["forester"] = resourcesVfs["textures/forester.png"].readBitmap()
        textures["flowerist"] = resourcesVfs["textures/flowerist.png"].readBitmap()
        textures["zombie"] = resourcesVfs["textures/zombie.png"].readBitmap()
        textures["wolf"] = resourcesVfs["textures/wolf.png"].readBitmap()
        textures["skeleton"] = resourcesVfs["textures/skeleton.png"].readBitmap()

        textures["nothing"] = resourcesVfs["textures/nothing.png"].readBitmap()
        textures["wooden sword"] = resourcesVfs["textures/wooden sword.png"].readBitmap()
        textures["stone sword"] = resourcesVfs["textures/stone sword.png"].readBitmap()
        textures["iron sword"] = resourcesVfs["textures/iron sword.png"].readBitmap()
        textures["legendary sword"] = resourcesVfs["textures/legendary sword.png"].readBitmap()
        textures["dragon sword"] = resourcesVfs["textures/dragon sword.png"].readBitmap()

        textures["health"] = resourcesVfs["textures/health.png"].readBitmap()*/
    }

    operator fun get(name: String?): Bitmap? {
        return textures[name]
    }
}
