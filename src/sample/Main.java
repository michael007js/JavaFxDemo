package sample;

import com.sun.javafx.application.PlatformImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.api.ApiApplication;
import sample.base.BaseApplication;
import sample.base.BaseTabModule;
import sample.constant.AppConstant;
import sample.utils.AlertUtils;

/**
 * @author Michael by SSS
 * @date 2021/9/14 21:51
 * @Description 主窗口
 */
public class Main extends BaseApplication {
    String title = AppConstant.APP_NAME + " V" + AppConstant.VERSION_NAME + " author by michael";

    @Override
    protected void onStart(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle(title);
        primaryStage.setResizable(false);
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root, controller.gridPaneRootParent.getMaxWidth(), controller.gridPaneRootParent.getMaxHeight());
        primaryStage.setScene(scene);
        primaryStage.show();

        listenTray(primaryStage);

    }

    @Override
    protected List<TrayMenuEvent> createTrayMenus(Stage primaryStage) {
        List<TrayMenuEvent> list = new ArrayList<>();
        list.add(new TrayMenuEvent(new MenuItem("启动Api"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApiApplication.start(6666, new DisposableObserver<String>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        PlatformImpl.runLater(new Runnable() {
                            @Override
                            public void run() {
                                primaryStage.setTitle(title+" 正在启动Api");
                            }
                        });

                    }

                    @Override
                    public void onNext(String s) {
                        PlatformImpl.runLater(new Runnable() {
                            @Override
                            public void run() {
                                primaryStage.setTitle(title+" "+s);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }));
        list.add(new TrayMenuEvent(new MenuItem("停止Api"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApiApplication.stop(new DisposableObserver<String>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        PlatformImpl.runLater(new Runnable() {
                            @Override
                            public void run() {
                                primaryStage.setTitle(title+" 正在停止Api");
                            }
                        });

                    }

                    @Override
                    public void onNext(String s) {
                        PlatformImpl.runLater(new Runnable() {
                            @Override
                            public void run() {
                                primaryStage.setTitle(title+" "+s);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }));
        list.add(new TrayMenuEvent(new MenuItem("主界面"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show(primaryStage);
            }
        }));
        list.add(new TrayMenuEvent(new MenuItem("最小化"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hide(primaryStage);
            }
        }));

        list.add(new TrayMenuEvent(new MenuItem("退出"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDestroy();
            }
        }));

        return list;
    }


}
