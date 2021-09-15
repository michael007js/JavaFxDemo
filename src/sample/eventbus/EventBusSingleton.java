package sample.eventbus;

/**
 * @author Michael by SSS
 * @date 2021/9/15 22:54
 * @Description 事件总线单例类
 */
public class EventBusSingleton {
    /**
     * 事件总线
     */
    private static com.google.common.eventbus.EventBus eventBus = new com.google.common.eventbus.EventBus();

    public static com.google.common.eventbus.EventBus getEventBus() {
        return eventBus;
    }
}
