package com.tiansu.energy.ltc.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * jwt用户传入，如有原登录接口 可以忽略
 * @author yuwen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {
    private String username;
    private String password;
}
