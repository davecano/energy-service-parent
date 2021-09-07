package com.tiansu.energy.ltc.common.anno;
import java.lang.annotation.*;

/**
 * 员工实体类
 *
 * @author ldd
 * @date 2021年01月19日下午1:34:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
