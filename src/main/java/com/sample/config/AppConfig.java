package com.sample.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
@Component
@Setter
@Slf4j
@EnableScheduling
public class AppConfig {
    @Value("${app.label}")
    private String label;

    @PostConstruct
    private void log() {
        StringBuilder builder = new StringBuilder(40);

        builder.append("Application Configuration:").append("\n")
                .append("\tlabel: ").append(label).append("\n");

        log.info(builder.toString());
    }
}
