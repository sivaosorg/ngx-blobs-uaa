package com.phuocnguyen.app.ngxblobsuaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.phuocnguyen.app.ngxblobsuaa",
                "com.phuocnguyen.app.ngxblobscache"
        }
)
public class NgxBlobsUaaApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        // SpringApplication.run(NgxBlobsUaaApplication.class, args);
        NgxBlobsUaaApplication.context = SpringApplication.run(NgxBlobsUaaApplication.class, args);
    }

    public static void restart() {
        context.close();
        NgxBlobsUaaApplication.context = SpringApplication.run(NgxBlobsUaaApplication.class);
    }

}
