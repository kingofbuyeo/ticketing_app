package com.yong.chul.ticketing_app.utils

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * <pre>
 * com.yong.chul.ticketing_app.utils
 *   RedisUtils
 * </pre>
 * @date : 2024. 7. 10. AM 9:46
 * @author : kim yong chul
 * @describe
 **/
@Component
class RedisUtils(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    private val TICKET_WAITING = "TICKET_WAITING"
    private val REVERVED_KEY = "RESERVED_USER_COUNT"

    fun delete(key: String) {
        redisTemplate.delete(key)
    }

    fun getValue(key: String): String? = redisTemplate.opsForValue().get(key).toString()

    fun zSetInit() {
        redisTemplate.opsForZSet()
    }

    fun setValues(userId: String) {
        redisTemplate.opsForZSet().add(
            TICKET_WAITING,
            userId,
            LocalDateTime.now().toEpochSecond(
                ZoneOffset.UTC,
            ).toDouble(),
        )
    }

    fun userIndex(userId: String): Long{
        return redisTemplate.opsForZSet().rank(TICKET_WAITING, userId)?.toLong() ?: -1
    }

    fun removeUserId(userId: String) {
        redisTemplate.opsForZSet().remove(TICKET_WAITING, userId)
    }

    fun increaseReservedKey(): Long {
        return redisTemplate.opsForValue().increment(REVERVED_KEY, 1) ?: -1
    }

    fun decreaseReservedKey() {
        redisTemplate.opsForValue().decrement(REVERVED_KEY, 1)
    }

    fun getReservedCount(): Long {
        return redisTemplate.opsForValue().get(REVERVED_KEY).toString().toLong()
    }
}
