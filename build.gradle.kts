import com.soywiz.korge.gradle.*

plugins {
    kotlin("multiplatform") version "1.7.10"
	alias(libs.plugins.korge)
}

korge {
	id = "com.sample.demo"

// To enable all targets at once

	targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets
	
	targetJvm()
	targetJs()
	targetDesktop()
	targetIos()
	targetAndroidIndirect() // targetAndroidDirect()

	serializationJson()
	//targetAndroidDirect()
}
