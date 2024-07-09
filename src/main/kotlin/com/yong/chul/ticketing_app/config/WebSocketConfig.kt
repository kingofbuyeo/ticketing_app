package com.yong.chul.ticketing_app.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(

): WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/ticket") //클라이언트에서 보낸 메세지를 받을 prefix
        registry.enableSimpleBroker("/wait") //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/reserved") //SockJS 연결 주소
            .setAllowedOriginPatterns("*")
            .withSockJS() //버전 낮은 브라우저에서도 적용 가능
    }
}