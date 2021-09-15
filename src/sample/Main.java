package sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

    @Override
    protected void onStart(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle(AppConstant.APP_NAME + " V" + AppConstant.VERSION_NAME + " author by michael");
        primaryStage.setResizable(false);
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root, controller.gridPaneRootParent.getMaxWidth(), controller.gridPaneRootParent.getMaxHeight());
        primaryStage.setScene(scene);
        primaryStage.show();

        listenTray(primaryStage);

    }

    @Override
    protected List<TrayMenuEvent> createTrayMenus(Stage primaryStage) {
        List<TrayMenuEvent> list=new ArrayList<>();
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
