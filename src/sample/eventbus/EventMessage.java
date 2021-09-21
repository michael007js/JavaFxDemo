package sample.eventbus;

/**
 * EventBus消息类
 *
 * @author Michael by SSS
 * @date 2021/9/15 22:32
 */
public class EventMessage {
    private int action;
    private Object object;

    public EventMessage(int action) {
        this.action = action;
    }

    public EventMessage(int action, Object object) {
        this.action = action;
        this.object = object;
    }

    public int getAction() {
        return action;
    }

    public Object getObject() {
        return object;
    }
}
