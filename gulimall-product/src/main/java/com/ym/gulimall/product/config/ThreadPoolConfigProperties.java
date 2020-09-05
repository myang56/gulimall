package com.ym.gulimall.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gulimall.thread")
public class ThreadPoolConfigProperties {

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer keepAliveTime;


    public static void main(String[] args) {

        ThreadPoolConfigProperties poolConfigProperties = new ThreadPoolConfigProperties();
        System.out.println(poolConfigProperties.getCorePoolSize());
    }

}