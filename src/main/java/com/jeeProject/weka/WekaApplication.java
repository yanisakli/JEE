package com.jeeProject.weka;

import com.jeeProject.weka.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class WekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WekaApplication.class, args);
    }


}