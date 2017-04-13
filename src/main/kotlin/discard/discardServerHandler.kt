package discard

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.nio.charset.Charset

/**
 * Created by sweety on 2016/8/25.
 */

class DiscardServerHandler : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, req: Any?) {
        if (ctx is ChannelHandlerContext) {
            ctx.write(req)
            println("${(req as ByteBuf).toString(Charset.defaultCharset())}")
            ctx.flush()
        }
    }

    @Suppress("OverridingDeprecatedMember")
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        if (cause is Throwable) cause.printStackTrace()
        if (ctx is ChannelHandlerContext) ctx.close()
    }
}