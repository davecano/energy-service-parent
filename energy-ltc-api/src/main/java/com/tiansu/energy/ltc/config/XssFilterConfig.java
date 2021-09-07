package com.tiansu.energy.ltc.config;

import cn.hutool.core.util.StrUtil;
import com.tiansu.energy.ltc.config.system.EnergyOtherConfig;
import com.tiansu.energy.ltc.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;


/**
 * xss过滤器配置
 * @author yuwen
 */
@Configuration
public class XssFilterConfig {

    @Resource
    private EnergyOtherConfig config;

    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns(StrUtil.split(config.getXss().getUrlPatterns(),","));
        registrationBean.setName("XssFilter");
        registrationBean.setOrder(9999);
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("excludes",config.getXss().getExcludes());
        initParameters.put("enabled",config.getXss().getEnabled());
        registrationBean.setInitParameters(initParameters);
        return registrationBean;
    }
}
