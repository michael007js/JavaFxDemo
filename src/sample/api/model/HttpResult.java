package sample.api.model;

/**
 * Created by Administrator on 2018/12/5.
 */

public class HttpResult<T> {

    private String code;
    private String message;
    private String fullMessage;
    private String timestamp;
    private boolean isSuccess;
    private T result;

    public HttpResult(T result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HttpResultTZY{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", fullMessage='" + fullMessage + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", isSuccess=" + isSuccess +
                ", result=" + result +
                '}';
    }
}
