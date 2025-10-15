package com.priyanshu.energy.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.priyanshu.energy.monitoring")
public class EnergyMonitoringSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyMonitoringSystemApplication.class, args);
    }

}
