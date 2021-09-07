package com.tiansu.energy.ltc.common.domain;

import lombok.Data;

/**
 *  正常请求 日志内容
 */
@Data
public class RequestInfo {
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private Object result;
    private Long timeCost;
}
