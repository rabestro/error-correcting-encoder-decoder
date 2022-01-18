package lv.id.jc.correcter;

import lv.id.jc.correcter.app.Application;
import lv.id.jc.correcter.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class Main {
    public static void main(String[] args) {
        var appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        var application = appContext.getBean("application", Application.class);

        application.run();
    }

}
