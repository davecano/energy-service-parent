package com.tiansu.energy.ltc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 缓存配置
 * @author yuwen
 */
@EnableCaching
@SpringBootConfiguration
public class CacheConfig {

    @Autowired
    private RedisConnectionFactory factory;

    /**
     * RedisCacheManager 注册
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(){
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 防止在序列化的过程中丢失对象的属性
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 开启实体类和json的类型转换
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                // 不缓存空值
                .disableCachingNullValues()
                // 1分钟过期
                .entryTtl(Duration.ofMinutes(1))
                ;
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }

    /**
     * springcache key生成策略
     * 如果配置了KeyGenerator ，在进行缓存的时候如果不指定key的话，
     * 最后会把生成的key缓存起来，如果同时配置了KeyGenerator 和key则优先使用key
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuffer key = new StringBuffer();
            key.append(target.getClass().getSimpleName() + "#" + method.getName() + "(");
            for (Object args : params) {
                key.append(args + ",");
            }
            key.deleteCharAt(key.length() - 1);
            key.append(")");
            return key.toString();
        };
    }

}
