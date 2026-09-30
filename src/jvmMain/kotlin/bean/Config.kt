package bean

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：bean.Config
 *** date：2023/12/31  14:19
 *** filename：bean.Config
 *** desc：json配置文件
 ***/

/** JSON持久化配置Bean（config.json，Base64编码存储） */
class Config {
    var version = 0 // 版本模式:0=侧键炼狱 1=K键炼狱
    var xbutton = 0 // 侧键功能:0=USP速点 1=快刀

    var pStart = 15 // 炼狱-间隔下限
    var pStop = 35 // 炼狱-间隔上限
    var pBegin = 120 // 炼狱-按下下限
    var pEnd = 180 // 炼狱-按下上限

    var uStart = 30 // USP-间隔下限
    var uStop = 60 // USP-间隔上限
    var uBegin = 40 // USP-按下下限
    var uEnd = 70 // USP-按下上限

    var auto = false; // 开机自启

    var license = false; // 授权状态
}
