package com.tiansu.energy.ltc.common.wrapper;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    @Override
    public String getHeader(String name) {
        String strHeader = super.getHeader(name);
        if(StrUtil.isEmpty(strHeader)){
            return strHeader;

        }
        return Jsoup.clean(super.getHeader(name), Whitelist.relaxed());
    }

    @Override
    public String getParameter(String name) {
        String strParameter = super.getParameter(name);
        if(StrUtil.isEmpty(strParameter)){
            return strParameter;
        }
        return Jsoup.clean(super.getParameter(name),Whitelist.relaxed());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values==null){
            return values;
        }
        int length = values.length;
        String[] escapseValues = new String[length];
        for(int i = 0;i<length;i++){
            //过滤一切可能的xss攻击字符串
            //simpleText
            escapseValues[i] =HtmlUtils.htmlEscape(values[i]);
            //escapseValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
            if(!StrUtil.equals(escapseValues[i],values[i])){
                log.info("xss字符串过滤前："+values[i]+"\r\n"+"过滤后："+escapseValues[i]);
            }
        }
        return escapseValues;
    }

}
