package com.github.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author Dooby Kim
 * @Date 2024/4/16 下午11:04
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload-dev.properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload {
    private String imageUserFaceLocation;
    private String imageServerUrl;
}
