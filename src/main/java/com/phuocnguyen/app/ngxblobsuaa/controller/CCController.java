package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobsuaa.NgxBlobsUaaApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/controls")
public class CCController {

    private final ApplicationContext applicationContext;

    @Autowired
    public CCController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("SameParameterValue")
    private void initiateAppShutdown(int returnCode) {
        SpringApplication.exit(applicationContext, () -> returnCode);
    }

    @GetMapping("/restart")
    void restart2() {
        Thread restartThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                NgxBlobsUaaApplication.restart();
            } catch (InterruptedException ignored) {
            }
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }

    @RequestMapping("/shutdown")
    public void shutdown() {
        initiateAppShutdown(0);
    }
}
