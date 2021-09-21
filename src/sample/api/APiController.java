package sample.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import sample.api.async.AsyncTask;
import sample.api.async.TaskThreadPoolConfig;
import sample.api.model.HttpResult;


@RestController
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class})
public class APiController extends AsyncTask {

    @Override
    public <T> HttpResult<T> doTask(Object object) {
        if ("String".equals(object.getClass().getSimpleName())) {
            return service.helloServer((String) object);
        }
        return new HttpResult(null);
    }

    @Autowired
    ApiServer service;

    @PostMapping(value = "/hello", produces = "application/json;charset=UTF-8")
    public <T> HttpResult<T> hello(HttpServletRequest httpServletRequest, @RequestBody(required = true) String input) {
        return doTask(input);
    }

}