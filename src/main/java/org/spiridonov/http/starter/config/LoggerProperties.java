package org.spiridonov.http.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "http.logging")
public class LoggerProperties {
    private boolean enabled;
    private HttpLoggerLevel level = HttpLoggerLevel.INFO;

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public HttpLoggerLevel getLevel() {
        return level;
    }
    public void setLevel(HttpLoggerLevel level) {
        this.level = level;
    }

}
