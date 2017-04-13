package vertx

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.MetricRegistry
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import java.util.concurrent.TimeUnit

/**
 * Created by sweety on 2016/8/27.
 */

class HelloWorldServer {
    companion object {
        @JvmStatic
        val metrics = MetricRegistry()
        @JvmStatic
        fun main(args : Array<String>) {
            startReport()
            val requestMetric = metrics.meter("requests")
            Vertx.vertx(VertxOptions().setEventLoopPoolSize(4).setWorkerPoolSize(4))
            .createHttpServer()
            ?.requestHandler(fun (req) {
                requestMetric.mark()
                req.response().end("hello, world")
            })
            ?.listen(8080, fun (handler) {
                if (handler.succeeded()) {
                    println("server started")
                } else {
                    handler.cause().printStackTrace()
                }
            })
        }
        @JvmStatic
        fun startReport() {
            val reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            reporter.start(1, TimeUnit.SECONDS)
        }
    }
}
