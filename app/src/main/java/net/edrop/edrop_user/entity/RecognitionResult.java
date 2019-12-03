package net.edrop.edrop_user.entity;

/**
 * Created by Android Studio.
 * User: zhanghaoyu
 * Date: 2019/12/3
 * Time: 19:41
 */
public class RecognitionResult {
    private Integer code;
    private String msg;
    private String newsList;

    public RecognitionResult() {
    }

    public RecognitionResult(Integer code, String msg, String newsList) {
        this.code = code;
        this.msg = msg;
        this.newsList = newsList;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNewsList() {
        return newsList;
    }

    public void setNewsList(String newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "RecognitionResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", newsList=" + newsList +
                '}';
    }
}
