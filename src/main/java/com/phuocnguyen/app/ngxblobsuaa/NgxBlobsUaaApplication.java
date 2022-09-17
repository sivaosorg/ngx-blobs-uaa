package com.phuocnguyen.app.ngxblobsuaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SuppressWarnings("SpellCheckingInspection")
@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.phuocnguyen.app.ngxblobsuaa",
                "com.phuocnguyen.app.ngxblobscache",
                "com.phuocnguyen.app.ngxblobso2jwt",
                "com.phuocnguyen.app.ngxblobsadvice"
        }
)
public class NgxBlobsUaaApplication extends ServletInitializer {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        NgxBlobsUaaApplication.context = SpringApplication.run(NgxBlobsUaaApplication.class, args);
    }

    public static void restart() {
        context.close();
        NgxBlobsUaaApplication.context = SpringApplication.run(NgxBlobsUaaApplication.class);
    }

}
