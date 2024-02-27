package com.github.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Dooby Kim
 * @Date 2024/2/28 上午12:12
 * @Version 1.0
 */
@Configuration
public class ServletConfig {
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                context -> context.setCookieProcessor(new LegacyCookieProcessor())
        );
    }
}
