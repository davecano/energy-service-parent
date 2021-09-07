package com.tiansu.energy.ltc.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 *  此处可以验证用户名密码等
 *  demo演示就硬编码了
 *  在线Bcrypt Generator 生成的密码
 * @author yuwen
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("tiansu".equals(username)) {
            return new User("tiansu", new BCryptPasswordEncoder().encode("password"),new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }
}
