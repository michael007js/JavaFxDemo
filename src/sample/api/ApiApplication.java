package sample.api;


import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SpringBootApplication
public class ApiApplication {
    private static ConfigurableApplicationContext configurableApplicationContext;

    public static void main(String[] args, int port, DisposableObserver<String> disposableObserver) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                if (configurableApplicationContext == null) {
                    configurableApplicationContext = SpringApplication.run(ApiApplication.class, "--server.port=" + port);
                    observableEmitter.onNext("Api服务启动完成，正在监听端口" + port);
                }else {
                    observableEmitter.onNext("Api服务已启动，正在监听端口" + port);
                }
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.single())
                .subscribe(disposableObserver);
    }

    public synchronized static void start(int port, DisposableObserver<String> disposableObserver) {
        main(new String[]{}, port, disposableObserver);

    }

    public synchronized static void stop( DisposableObserver<String> disposableObserver) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                if (configurableApplicationContext == null) {
                    Observable.error(new RuntimeException("上下文为空，Api服务停止失败"));
                } else {
                    int code = SpringApplication.exit(configurableApplicationContext, new ExitCodeGenerator() {
                        @Override
                        public int getExitCode() {
                            return 0;
                        }
                    });
                    configurableApplicationContext = null;
                    observableEmitter.onNext("Api服务已停止");
                    observableEmitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.single())
                .subscribe(disposableObserver);
    }

}
