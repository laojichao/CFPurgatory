# CFPurgatory

## 项目概述
基于 Kotlin + Compose Desktop 的穿越火线 (CrossFire) 游戏桌面宏工具。提供炼狱快速连点、USP 快速射击、快速切刀、后坐力补偿等功能，使用高斯分布随机延迟模拟人类操作。

## 技术栈
- **语言**: Kotlin
- **框架**: Jetpack Compose Desktop (Compose Multiplatform)
- **JVM**: 11
- **全局钩子**: JNativeHook
- **网络**: OkHttp + Retrofit (授权验证)
- **UI 主题**: FlatLaf
- **响应式**: RxJava3
- **原生访问**: JNA
- **加密**: BouncyCastle
- **JSON**: Gson
- **日志**: Log4j

## 项目结构
```
src/jvmMain/kotlin/
├── Macro.kt                    # 核心宏引擎，鼠标/键盘事件监听
├── thread/
│   ├── GaussianPurgatory.kt    # 炼狱模式宏线程 (高斯延迟)
│   ├── GaussianUSP.kt          # USP 手枪宏线程
│   └── KnifeThread.kt          # 快速切刀宏线程
├── ui/                         # UI 组件 (ActiveCodeDialog, MachineCodeDialog, TrayIcon)
└── utils/
    └── MachineCodeUtils.kt     # 机器码生成 (授权验证)
```

## 核心功能
- 炼狱武器快速连点 (高斯随机延迟)
- USP 手枪快速射击
- 快速切刀
- 后坐力补偿
- 全局鼠标/键盘钩子 (游戏后台生效)
- 机器码授权验证

## 开发注意事项
- JNativeHook 用于全局输入事件捕获，需要系统级权限
- 高斯分布延迟模拟人类操作节奏
- 授权系统基于机器码 + 网络验证
- Compose Desktop UI 非 Android Compose
