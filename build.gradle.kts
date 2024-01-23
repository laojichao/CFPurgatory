import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.ia0l6"
version = "1.0.1"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}


kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.formdev:flatlaf:3.2.5")
                implementation("com.squareup.okhttp3:okhttp:3.12.12")
                implementation("com.squareup.okhttp3:logging-interceptor:3.12.12")
                runtimeOnly ("com.squareup.okhttp3:okhttp-bom:3.12.12")
                implementation("com.google.code.gson:gson:2.9.0")
                implementation("com.squareup.retrofit2:retrofit:2.9.0")
                implementation("com.squareup.retrofit2:converter-gson:2.9.0")
                implementation("com.github.kwhat:jnativehook:2.2.2")
                implementation("commons-codec:commons-codec:1.16.0")
                implementation("org.apache.commons:commons-math3:3.6.1")
                implementation("io.reactivex.rxjava3:rxjava:3.1.8")
                implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
                implementation("org.greenrobot:eventbus-java:3.3.1")
                implementation("org.apache.logging.log4j:log4j-api:2.12.2")
                implementation("org.apache.logging.log4j:log4j-core:2.12.2")
                implementation("net.java.dev.jna:jna:5.12.1")
                implementation("net.java.dev.jna:jna-platform:5.12.1")

            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "ui.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "CFPurgatory"     //应用程序名称（默认值：Gradle 项目名称）
            packageVersion = "1.0.1"        //应用程序的版本（默认值：Gradle 项目的版本)
            copyright = "Copyright © 2024 Ia0l6"  //应用程序的版权
            vendor = "AutoTech"                   //应用程序的供应商
            description = "Purgatory Tool"        // 应用程序的描述
            licenseFile //应用程序的许可证
            linux {
                iconFile.set(file("src/jvmMain/resources/icon.png"))
            }
            windows {
                console = false  //为应用程序添加一个控制台启动器
                dirChooser = true   //允许在安装过程中自定义安装路径
                exePackageVersion = "1.0.1" //特定于 pkg 的包版本
                menu = true                 //开始菜单
                menuGroup = "CFPurgatory" //将应用程序添加到指定的开始菜单组
                msiPackageVersion = "1.0.1" //特定于 msi 的软件包版本
                perUserInstall = false      //私有目录安装允许在每个用户的基础上安装应用程序
                shortcut = true             //创建桌面开始菜单
                upgradeUuid = "89E7266F-4DB7-4961-8D55-D5BBE588699E" //一个唯一的 ID，当更新的版本比安装的版本更新时，它使用户能够通过安装程序更新应用程序。对于单个应用程序，该值必须保持不变
                iconFile.set(file("src/jvmMain/resources/icon.ico"))
            }
        }
        //msi release 打包需要
        jvmArgs("-Dfile.encoding=GBK")
        buildTypes.release.proguard {
            obfuscate.set(false)
            configurationFiles.from(project.file("proguard-rules.pro"))
        }

    }
}

// 避免每次 clean 后都需要再次下载, 这里直接在根目录放置下载好的文件, 用 task 进行拷贝
//task("copyWix311", Copy::class) {
//    from("wix311.zip")
//    into("build/wixToolset")
//}
//
//// 需要在 afterEvaluate 中处理, 否则报找不到 packageMsi 这个 task 的错误
//afterEvaluate {
//    tasks.getByName("packageMsi").dependsOn("copyWix311")
//    tasks.getByName("downloadWix").enabled = false
//
//    if (file("build/wixToolset/unpacked").exists()) {
//        tasks.getByName("unzipWix").enabled = false
//    }
//}
