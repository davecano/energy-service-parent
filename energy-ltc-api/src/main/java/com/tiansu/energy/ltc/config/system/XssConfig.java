package com.tiansu.energy.ltc.config.system;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class XssConfig {

    private String enabled;

    private String excludes;

    private String urlPatterns;
}
