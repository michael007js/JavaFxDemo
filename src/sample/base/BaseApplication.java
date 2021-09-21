package sample.base;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Controller;
import sample.constant.AppConstant;
import sample.eventbus.EventBusSingleton;
import sample.eventbus.EventMessage;
import sample.eventbus.OnMessageEventListener;
import sample.utils.AlertUtils;
import sample.utils.LogUtils;

/**
 * 自定义系统托盘
 *
 * @author Michael by SSS
 * @date 2021/9/15 19:44
 * @Description 请完善本类的说明
 */
public abstract class BaseApplication extends Application implements OnMessageEventListener {
    /**
     * 主控
     */
    protected Controller controller;
    /**
     * 模块列表
     */
    protected List<BaseTabModule> moduleList = new ArrayList<>();
    /**
     * 托盘菜单列表
     */
    private List<TrayMenuEvent> menuEventList;
    /**
     * 托盘图标
     */
    private static TrayIcon trayIcon;
    /**
     * 托盘鼠标事件
     */
    private static MouseListener mouseListener;


    @Override
    public void start(Stage primaryStage) {
        try {
            EventBusSingleton.getEventBus().register(this);
            onStart(primaryStage);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (AlertUtils.showConfirm("退出", "请确认您的操作", "您真的要关闭" + AppConstant.APP_NAME + "吗？")) {
                        for (BaseTabModule baseTabModule : moduleList) {
                            baseTabModule.releaseAll();
                        }
                        clear();
                        onDestroy();
                    } else {
                        event.consume();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 窗口被展现时调用
     */
    protected abstract void onStart(Stage primaryStage) throws Exception;

    /**
     * 窗口被关闭前调用
     */
    protected void onDestroy() {
        EventBusSingleton.getEventBus().unregister(this);
        System.exit(0);
    }

    /**
     * 如果发送了EventMessage消息，会进入到该函数的处理
     * @param event 消息
     */
    @Override
    public void onMessageEvent(EventMessage event) {

    }

    /**
     * 创建托盘菜单
     *
     * @param primaryStage
     * @return 托盘菜单列表
     */
    protected List<TrayMenuEvent> createTrayMenus(Stage primaryStage) {
        return null;
    }

    /**
     * 更改系统托盘所监听的Stage
     */
    protected void listenTray(Stage primaryStage) {
        try {
            menuEventList = createTrayMenus(primaryStage);
            if (menuEventList == null) {
                return;
            }
            //执行stage.close()方法,窗口不直接退出
            Platform.setImplicitExit(false);
            //菜单项(打开)中文乱码的问题是编译器的锅,如果使用IDEA,需要在Run-Edit Configuration在LoginApplication中的VM Options中添加-Dfile.encoding=GBK
            //如果使用Eclipse,需要右键Run as-选择Run Configuration,在第二栏Arguments选项中的VM Options中添加-Dfile.encoding=GBK
            //此处不能选择ico格式的图片,要使用16*16的png格式的图片
            URL url = BaseApplication.class.getResource("/resources/icon-tray.png");
            Image image = Toolkit.getDefaultToolkit().getImage(url);
            //系统托盘图标
            trayIcon = new TrayIcon(image);

            //检查系统是否支持托盘
            if (!SystemTray.isSupported()) {
                //系统托盘不支持
                LogUtils.e(Thread.currentThread().getStackTrace()[1].getClassName() + ":系统托盘不支持");
                return;
            }
            //设置图标尺寸自动适应
            trayIcon.setImageAutoSize(true);
            //系统托盘
            SystemTray tray = SystemTray.getSystemTray();
            //弹出式菜单组件
            final PopupMenu popup = new PopupMenu();
            for (TrayMenuEvent trayMenuEvent : menuEventList) {
                trayMenuEvent.menuItem.removeActionListener(trayMenuEvent.actionListener);
                trayMenuEvent.menuItem.addActionListener(trayMenuEvent.actionListener);
                popup.add(trayMenuEvent.menuItem);
            }
            trayIcon.setPopupMenu(popup);
            //鼠标移到系统托盘,会显示提示文本
            trayIcon.setToolTip(AppConstant.APP_NAME);
            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
            //系统托盘添加失败
            LogUtils.e(Thread.currentThread().getStackTrace()[1].getClassName() + ":系统添加失败");
        }

        //鼠标行为事件: 单击显示stage
        if (mouseListener == null) {
            mouseListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //鼠标左键双击
                    if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                        show(primaryStage);
                    }
                }
            };
        }
        //给系统托盘添加鼠标响应事件
        trayIcon.removeMouseListener(mouseListener);
        trayIcon.addMouseListener(mouseListener);
    }

    /**
     * 隐藏窗口
     */
    public void hide(Stage stage) {
        Platform.runLater(() -> {
            //如果支持系统托盘,就隐藏到托盘
            if (SystemTray.isSupported()) {
                //stage.hide()与stage.close()等价
                stage.hide();
            }
        });
    }

    /**
     * 点击系统托盘,显示界面(并且显示在最前面,将最小化的状态设为false)
     */
    public void show(Stage stage) {
        //点击系统托盘
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (stage.isIconified()) {
                    stage.setIconified(false);
                }
                if (!stage.isShowing()) {
                    stage.show();
                }
                stage.toFront();
            }
        });
    }


    /**
     * 清理垃圾
     */
    public void clear() {
        if (trayIcon != null) {
            trayIcon.removeMouseListener(mouseListener);
        }
        if (menuEventList != null) {
            for (TrayMenuEvent trayMenuEvent : menuEventList) {
                trayMenuEvent.menuItem.removeActionListener(trayMenuEvent.actionListener);
            }
        }
    }

    /**
     * 托盘菜单事件类
     */
    public static class TrayMenuEvent {
        /**
         * 菜单
         */
        private MenuItem menuItem;
        /**
         * 点击事件
         */
        private ActionListener actionListener;

        public TrayMenuEvent(MenuItem menuItem, ActionListener actionListener) {
            this.menuItem = menuItem;
            this.actionListener = actionListener;
        }
    }
}