package http

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.http.*

/**
 * Created by sweety on 2016/8/27.
 */

class HttpHelloWorldServerHandler : ChannelInboundHandlerAdapter() {
    private val CONTENT = "Hello, World"

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        if (ctx is ChannelHandlerContext) ctx.flush()
    }

    override fun channelRead(ctx: ChannelHandlerContext?, req: Any?) {
        if (req is HttpRequest) {
            if (HttpHeaders.is100ContinueExpected(req)) {
                ctx?.write(DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE))
            }
            val keepAlive = HttpHeaders.isKeepAlive(req)
            val response = DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(CONTENT.toByteArray()))
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain")
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes())
            if (!keepAlive) {
                ctx?.write(response)?.addListener(io.netty.channel.ChannelFutureListener.CLOSE)
            } else {
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE)
                ctx?.write(response)
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }
}
