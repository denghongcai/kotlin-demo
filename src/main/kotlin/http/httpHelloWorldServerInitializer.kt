package http

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.ssl.SslContext

/**
 * Created by sweety on 2016/8/27.
 */

class HttpHelloWorldServerInitializer(sslCtx : SslContext?) : ChannelInitializer<io.netty.channel.Channel>() {
    val sslCtx = sslCtx
    override fun initChannel(ch: Channel?) {
        if (ch is Channel) {
            val p = ch.pipeline()
            if (sslCtx != null) {
                p.addLast(sslCtx.newHandler(ch.alloc()))
            }
            p.addLast(HttpServerCodec())
            p.addLast(HttpHelloWorldServerHandler())
        }
    }
}