package org.spiridonov.http.starter.config;


import org.spiridonov.http.starter.HttpLoggerHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggerProperties.class)
public class HttpLoggerAutoConfiguration {

    private final LoggerProperties loggerProperties;

    public HttpLoggerAutoConfiguration(LoggerProperties loggerProperties) {
        this.loggerProperties = loggerProperties;
    }

    @Bean
    @ConditionalOnProperty(prefix = "http.logging", name = "enabled", havingValue = "true", matchIfMissing = false)
    public HttpLoggerHandler httpLoggerConfiguration() {
        return new HttpLoggerHandler(loggerProperties);
    }

}
