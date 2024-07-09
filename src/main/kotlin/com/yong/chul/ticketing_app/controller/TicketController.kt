package com.yong.chul.ticketing_app.controller

import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @MessageMapping("check/{key}")
    @SendTo("/wait/ticket/{key}")
    fun reservedTicket(key: String): String {

        logger.info("RECEIVED [$key]")
        return key
    }
}