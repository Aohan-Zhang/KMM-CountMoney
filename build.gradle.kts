plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
}
buildscript {
    dependencies{
        classpath("app.cash.sqldelight:gradle-plugin:2.0.0")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.0")


    }
}
