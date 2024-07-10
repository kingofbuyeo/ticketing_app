package com.yong.chul.ticketing_app.controller

import com.yong.chul.ticketing_app.usecase.dto.ReservedTicketDTO
import com.yong.chul.ticketing_app.utils.RedisUtils
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController(
    private val redisUtils: RedisUtils
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @MessageMapping("check/{key}")
    @SendTo("/wait/ticket/{key}")
    fun reservedTicket(key: String): ReservedTicketDTO {
        if(redisUtils.increaseReservedKey() > 1) {
            redisUtils.setValues(key)
            return ReservedTicketDTO("WAIT", redisUtils.userIndex(key))
        }
        logger.info("RECEIVED [$key]")
        return ReservedTicketDTO("DONE", -1)
    }
}