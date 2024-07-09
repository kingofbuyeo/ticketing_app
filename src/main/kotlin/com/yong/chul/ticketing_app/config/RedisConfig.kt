package com.yong.chul.ticketing_app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
//@EnableCaching
@EnableRedisRepositories(basePackages = ["com.yong.chul.ticketing_app.model.redis"])
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    val host: String,
    @Value("\${spring.data.redis.port}")
    val port: Int
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory
    {
        return LettuceConnectionFactory (host, port);
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any>
    {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory()
        redisTemplate.keySerializer = StringRedisSerializer()
        return redisTemplate;
    }


}