package com.sea.hw.train;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainApplication.class, args);
        TrainManagementSystem trainManagementSystem = new TrainManagementSystem();
        trainManagementSystem.runTrainSystemCalculator();
    }
}
