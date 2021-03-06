package sample.base;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import sample.Controller;
import sample.eventbus.EventBusSingleton;
import sample.eventbus.OnMessageEventListener;
import sample.eventbus.EventMessage;
import sample.utils.MouseKeyboardListenerHelper;

/**
 * @author Michael by SSS
 * @date 2021/9/14 21:50
 * @Description 模块基类
 */
public abstract class BaseTabModule implements OnMessageEventListener, NativeMouseListener, NativeMouseMotionListener, NativeMouseWheelListener, NativeKeyListener {
    private MouseKeyboardListenerHelper mouseKeyboardListenerHelper = new MouseKeyboardListenerHelper();
    /**
     * 计时器
     */
    protected DisposableObserver disposableObserver;
    /**
     * 主控
     */
    protected Controller controller;

    public BaseTabModule(Controller controller) {
        this.controller = controller;
        EventBusSingleton.getEventBus().register(this);
        onInitialize();
    }

    /**
     * 如果发送了EventMessage消息，会进入到该函数的处理
     * @param event 消息
     */
    @Override
    public void onMessageEvent(EventMessage event) {

    }

    /**
     * 释放资源
     */
    public void releaseAll() {
        EventBusSingleton.getEventBus().unregister(this);
        unRegisterTimer();
        mouseKeyboardListenerHelper.unRegister();
    }

    /**
     * 模块被初始化
     */
    protected abstract void onInitialize();

    /**
     * 注册计时器
     *
     * @param period    间隔
     * @param unit      单位
     * @param scheduler 线程调度器
     */
    protected void registerTimer(long period, TimeUnit unit, Scheduler scheduler) {
        disposableObserver = new DisposableObserver<Long>() {

            @Override
            protected void onStart() {
                super.onStart();
                onNext(0L);
            }

            @Override
            public void onNext(Long aLong) {
                onTimerInterval(aLong);
            }

            @Override
            public void onError(Throwable throwable) {
                onTimerError(throwable);
            }

            @Override
            public void onComplete() {
                onTimerComplete();
            }
        };
        Observable.interval(period, unit)
                .subscribeOn(scheduler)
                .subscribe(disposableObserver);
    }

    /**
     * 计时器计时中触发
     *
     * @param along
     */
    protected void onTimerInterval(long along) {

    }

    /**
     * 计时器出错时触发
     *
     * @param throwable 错误栈
     */
    protected void onTimerError(Throwable throwable) {

    }

    /**
     * 计时器结束时触发
     */
    protected void onTimerComplete() {

    }

    /**
     * 释放计时器
     */
    protected void unRegisterTimer() {
        if (disposableObserver != null) {
            if (disposableObserver.isDisposed()) {
                disposableObserver.dispose();
            }
        }
    }


    /**
     * 注册键盘鼠标监听
     *
     * @param exitByEsc 按esc退出监听
     * @param keyboard  监听键盘
     * @param mouse     监听鼠标
     */
    protected void registerKeyboardAndMouseListen(boolean exitByEsc, boolean keyboard, boolean mouse) {
        if (mouse) {
            mouseKeyboardListenerHelper.setNativeMouseListener(this);
            mouseKeyboardListenerHelper.setNativeMouseWheelListener(this);
            mouseKeyboardListenerHelper.setNativeMouseMotionListener(this);
        }
        if (keyboard) {
            mouseKeyboardListenerHelper.setNativeKeyListener(this);
        }
        if (mouse || keyboard) {
            mouseKeyboardListenerHelper.register(exitByEsc);
        }
    }

    /**
     * 鼠标被点击时触发
     *
     * @param nativeMouseEvent 鼠标事件
     */
    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    /**
     * 鼠标被按下时触发
     *
     * @param nativeMouseEvent 鼠标事件
     */
    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    /**
     * 鼠标被释放时触发
     *
     * @param nativeMouseEvent 鼠标事件
     */
    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }

    /**
     * 鼠标滚轮滑动
     *
     * @param nativeMouseWheelEvent 滚轮事件
     */
    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeMouseWheelEvent) {

    }

    /**
     * 鼠标移动
     *
     * @param nativeEvent 鼠标移动事件
     */
    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeEvent) {

    }

    /**
     * 鼠标拖动
     *
     * @param nativeEvent 鼠标拖动事件
     */
    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeEvent) {

    }

    /**
     * 按键被键入字符时触发
     *
     * @param nativeKeyEvent 按键事件
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    /**
     * 按键被按下时触发
     *
     * @param nativeKeyEvent 按键事件
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

    }

    /**
     * 按键被释放时触发
     *
     * @param nativeKeyEvent 按键事件
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
