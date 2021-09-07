package com.tiansu.energy.ltc.handler;

import com.tiansu.energy.ltc.common.domain.ResponseDTO;
import com.tiansu.energy.ltc.interceptor.ResponseResultInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * 返回体包装处理
 *
 * @author yuwen
 */
@ControllerAdvice
@Slf4j
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    //是否请求 包含了 包装注解标记 没有直接返回
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        if (null == request) {
            return false;
        }
        Object responseResultAnn = request.getAttribute(ResponseResultInterceptor.RESPONSE_RESULT_ANN);
        return responseResultAnn != null;


    }

    @ResponseBody

    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            log.debug(exception.getLocalizedMessage());
            return ResponseDTO.failure(400, exception.getMessage());
        }
        return ResponseDTO.failure(400, ex.getMessage());
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.debug("进入 返回体 重写格式 处理中");
        if (body instanceof ResponseDTO) {
            return body;
        }
        return ResponseDTO.success(body);
    }
}
