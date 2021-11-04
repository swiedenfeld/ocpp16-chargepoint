package chargepoint.rest

import io.micronaut.context.annotation.Property
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.websocket.WebSocketClient
import jakarta.inject.Inject
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Controller("/chargepoint")
class RestController(@Inject val webSocketClient: WebSocketClient, @Property(name = "url") val url: String) {

    @Get("/boot")
    fun boot() {
        webSocketClient.connect(OcppWebSocket::class.java, url)
            .subscribe(object : Subscriber<OcppWebSocket> {
                override fun onSubscribe(p0: Subscription?) {
                    p0!!.request(1)
                }

                override fun onNext(p0: OcppWebSocket?) {
                    p0!!.send("[2,\n" +
                            "\"19223201\",\n" +
                            "\"BootNotification\",\n" +
                            "{\"chargePointVendor\": \"ACME\", \"chargePointModel\": \"WALLBOX\"}\n" +
                            "]")
                }

                override fun onError(p0: Throwable?) {
                    println("Error")
                }

                override fun onComplete() {
                    println("Complete")
                }
            })
    }

}