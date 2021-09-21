package sample.api.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import sample.api.model.HttpResult;

/**
 * 异步任务
 */
@Component
public class AsyncTask {

    @Async("SSSTaskAsyncPool")  //SSSTaskAsyncPool 即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
    public <T> HttpResult<T> doTask(Object object) {
        return null;
    }
}