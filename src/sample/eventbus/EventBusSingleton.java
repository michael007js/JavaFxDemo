package sample.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * @author Michael by SSS
 * @date 2021/9/15 22:54
 * @Description 事件总线单例类
 */
public class EventBusSingleton {
    /**
     * 事件总线
     */
    private static EventBus eventBus = new EventBus();

    public static EventBus getEventBus() {
        return eventBus;
    }
}
