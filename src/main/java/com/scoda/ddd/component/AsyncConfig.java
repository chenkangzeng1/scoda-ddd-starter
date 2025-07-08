package com.scoda.ddd.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Common CQRS async thread pool configuration, supports starter configuration override.
 *
 * @author kangzeng.ckz
 * @since 2025/7/3
 **/
@Data
@ConfigurationProperties(prefix = "scoda.ddd.cqrs.async")
public class AsyncConfig {
    private int corePoolSize = 8;
    private int maxPoolSize = 16;
    private int queueCapacity = 100;
    private String threadNamePrefix = "cqrs-async-";
} 