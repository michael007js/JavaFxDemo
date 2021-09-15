package sample.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @author Michael by SSS
 * @date 2021/9/15 22:43
 * @Description 事件监听者
 */
public interface OnMessageEventListener {
    /**
     * 如果发送了EventMessage消息，会进入到该函数的处理
     *
     * @param event 消息
     */
    @Subscribe
     void onMessageEvent(EventMessage event);
}
