package sample.api;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import sample.api.model.HttpResult;
import sample.utils.TimeUtils;

@Service
public class ApiServer {

    public <T> HttpResult<T> helloServer(String data) {
        HttpResult httpResult = new HttpResult("this is a message from server:hello client");
        httpResult.setCode("1");
        httpResult.setFullMessage("ok");
        httpResult.setMessage("ok");
        httpResult.setTimestamp(TimeUtils.getNowString());
        httpResult.setSuccess(true);
        return httpResult;
    }

}