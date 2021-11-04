package chargepoint.rest

import io.micronaut.http.HttpRequest
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.ClientWebSocket
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen

@ClientWebSocket(subprotocol = "ocpp1.6")
abstract class OcppWebSocket : AutoCloseable {

    @OnOpen
    fun onOpen(session: WebSocketSession, request: HttpRequest<*>){
    }

    @OnMessage
    fun onMessage(message: String) {
        println("Received $message")
    }

    abstract fun send(message: String)

}