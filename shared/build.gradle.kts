import de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp") version "1.9.0-1.0.12"
    id("de.jensklingenberg.ktorfit") version "1.0.0"
    id("app.cash.sqldelight")
    id("kotlinx-serialization")
}

val ktorVersion = "2.3.3"
val ktorfitVersion = "1.4.4"
val sqlDelightVersion = "2.0.0"
val dataStoreVersion = "1.1.0-alpha04"

configure<KtorfitGradleConfiguration> {
    version = ktorfitVersion
}

kotlin {
    android()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {

        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val voyagerVersion = "1.0.0-rc06"
                implementation("cafe.adriel.voyager:voyager-kodein:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                implementation("org.kodein.di:kodein-di:7.20.1")
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation("com.moriatsushi.insetsx:insetsx:0.1.0-alpha10")
                implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")
                implementation("org.kodein.di:kodein-di-framework-compose:7.20.1")
                implementation("app.cash.sqldelight:runtime:$sqlDelightVersion")
                implementation("app.cash.sqldelight:coroutines-extensions:$sqlDelightVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("androidx.datastore:datastore-preferences-core:$dataStoreVersion")
                implementation("androidx.datastore:datastore-core-okio:$dataStoreVersion")
                implementation("co.touchlab:kermit:2.0.0-RC5")
                implementation("dev.icerock.moko:media:0.11.0")
                implementation("dev.icerock.moko:media-compose:0.11.0")
                implementation("dev.icerock.moko:permissions:0.16.0")
                implementation("dev.icerock.moko:permissions-compose:0.16.0")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
                implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
dependencies {
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspIosSimulatorArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
}
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("link.peipei.countmoney")
        }
    }
}
