package bean


/****
 *** author：lao
 *** package：bean
 *** project：CFPurgatory
 *** name：Delay
 *** date：2024/1/3  22:08
 *** filename：Delay
 *** desc：延迟Bean
 ***/

/** 延迟配置Bean：按下时长与点击间隔的采样区间，供高斯换算 */
class Delay {
    @Volatile var start = 100; // 点击间隔下限(ms)
    @Volatile var stop = 100; // 点击间隔上限(ms)
    @Volatile var begin = 100; // 按下时长下限(ms)
    @Volatile var end = 100; // 按下时长上限(ms)

    constructor(start: Int, stop: Int, begin: Int, end: Int) {
        this.start = start
        this.stop = stop
        this.begin = begin
        this.end = end
    }

    constructor()


}
