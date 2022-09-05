import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korim.color.*
import com.soywiz.korinject.*
import com.soywiz.korma.geom.*
import kotlin.reflect.*

suspend fun main() = Korge(Korge.Config(module = MainModule))

object MainModule : Module() {
    override val bgcolor: RGBA = Colors["#36a6e3"]
    override val mainScene: KClass<out Scene> = PlayScene::class
    override val size = SizeInt(900, 900)
    override val windowSize = SizeInt(900, 900)

    override suspend fun AsyncInjector.configure() {
        mapPrototype { PlayScene() }
    }
}
