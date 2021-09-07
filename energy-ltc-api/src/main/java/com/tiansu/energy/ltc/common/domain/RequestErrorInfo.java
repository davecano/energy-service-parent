package com.tiansu.energy.ltc.common.domain;

import lombok.Data;

/**
 *  请求报错日志记录信息
 * @author  ldd
 */
@Data
public class RequestErrorInfo {
    private String           ip;
    private String           url;

    /**
     * post get
     */
    private String           httpMethod;
    private String           classMethod;
    private Object           requestParams;
    private RuntimeException exception;
}


