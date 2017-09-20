package cn.learncoding.vo;

/**
 * Created by kou on 2017/9/20.
 */
public class ResultVO<T> {

    private int flag;
    private String msg;
    private T data;

    private static  final ResultVO<Object> DEFAULT_SUCCESS_RESULT = new ResultVO(0, "success", null);

    private static  final ResultVO<Object> DEFAULT_ERROR_RESULT = new ResultVO(-1, "system error", null);

    public ResultVO(int flag, String msg, T data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public static ResultVO<Object> successResult(){
        return DEFAULT_SUCCESS_RESULT;
    }

    public static <T> ResultVO<T> successResult(T data){
        return successResult("success", data);
    }

    public static <T> ResultVO<T>  successResult(String msg, T data){
        return new ResultVO<>(0, msg, data);
    }

    public static ResultVO<Object> errorResult(){
        return DEFAULT_ERROR_RESULT;
    }

    public static <T> ResultVO<T>  errorResult(String msg){
        return new ResultVO<>(0, msg, null);
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
