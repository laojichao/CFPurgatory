pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven ("https://jitpack.io" )
        //阿里云镜像
        maven ( "https://maven.aliyun.com/repository/google" )
        maven ( "https://maven.aliyun.com/repository/public")
        maven ( "https://maven.aliyun.com/repository/gradle-plugin")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

rootProject.name = "CFPurgatory"

