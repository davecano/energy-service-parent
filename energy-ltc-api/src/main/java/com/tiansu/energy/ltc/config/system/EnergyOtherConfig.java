package com.tiansu.energy.ltc.config.system;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统的一些配置
 * @author yuwen
 */
@ConfigurationProperties(prefix = "energy")
@Component
@Data
public class EnergyOtherConfig {

    private XssConfig xss;

    private LogConfig log;
}
