package bean

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：bean.RxCode
 *** date：2024/1/2  15:41
 *** filename：bean.RxCode
 *** desc：
 ***/

class RxCode {
    companion object {
        val SUCCESS = 200;
        val ERROR = 500;
        val TIMEOUT = 504;
        val UNAUTHORIZED = 401;
        val FORBIDDEN = 403;
        val NOT_FOUND = 404;
        val BAD_REQUEST = 400;
        val TEMPORARY_USE = 1001;    //临时使用
        val PERMANENT_USE = 1002;    //永久使用
        val INPUT_DIALOG = 1003;    //输入框
        val MACHINE_DIALOG = 1004;  //消息框
        val ACTIVE_DIALOG = 1005;  //消息框
        val MESSAGE_DIALOG = 1006;  //消息框
        val LICENSE_INVALID = 1007; //到期
        val LICENSE_NOTUSED = 1008;
        val LICENSE_ERROR = 1009;   //错误
        val TEMPORARY_ACTIVE = 1010;    //临时使用
        val PERMANENT_ACTIVE = 1011;    //永久使用
    }
}