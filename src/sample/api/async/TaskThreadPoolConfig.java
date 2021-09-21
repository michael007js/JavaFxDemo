package sample.api.async;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置属性类
 */
@ConfigurationProperties(prefix = "task.pool")
public class TaskThreadPoolConfig {

    /**
     * 核心线程数
     * @return
     */
    public int getCorePoolSize() {
        return 100;
    }

    /**
     * 线程池最大线程数
     * @return
     */
    public int getMaxPoolSize() {
        return 10000;
    }

    /**
     * @return
     */
    public int getKeepAliveSeconds() {
        return 300;
    }

    /**
     * 线程队列容量
     * @return
     */
    public int getQueueCapacity() {
        return 10000;
    }
}