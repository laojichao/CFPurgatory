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

class Delay {
    @Volatile var start = 100;
    @Volatile var stop = 100;
    @Volatile var begin = 100;
    @Volatile var end = 100;

    constructor(start: Int, stop: Int, begin: Int, end: Int) {
        this.start = start
        this.stop = stop
        this.begin = begin
        this.end = end
    }

    constructor()


}