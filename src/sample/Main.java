package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.base.BaseTabModule;
import sample.constant.AppConstant;
import sample.utils.AlertUtils;

/**
 * @author Michael by SSS
 * @date 2021/9/14 21:51
 * @Description 主窗口
 */
public class Main extends Application {
    /**
     * 主控
     */
    private Controller controller;
    /**
     * 模块列表
     */
    private List<BaseTabModule> moduleList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        initialize(primaryStage);

    }

    /**
     * 初始化
     *
     * @param primaryStage
     */
    private void initialize(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (AlertUtils.showConfirm("退出", "请确认您的操作", "您真的要关闭" + AppConstant.APP_NAME + "吗？")) {
                    for (BaseTabModule baseTabModule : moduleList) {
                        baseTabModule.releaseAll();
                    }
                    System.exit(0);
                } else {
                    event.consume();
                }

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
