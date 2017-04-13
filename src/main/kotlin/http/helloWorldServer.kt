package http

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate

/**
 * Created by sweety on 2016/8/27.
 */

class HttpHelloWorldServer {
    companion object {
        @JvmStatic
        val SSL = System.getProperty("ssl") != null
        @JvmStatic
        val PORT = Integer.parseInt(System.getProperty("port", if (SSL) "8443" else "8080"))
        @JvmStatic
        fun main(args: Array<String>) {
            val sslCtx : SslContext?
            if (SSL) {
                val ssc = SelfSignedCertificate()
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
            } else {
                sslCtx = null
            }

            val bossGroup = NioEventLoopGroup(8)
            val workerGroup = NioEventLoopGroup(8)
            try {
                val b = ServerBootstrap()
                b.option(ChannelOption.SO_BACKLOG, 1024)
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel::class.java)
                        .childHandler(HttpHelloWorldServerInitializer(sslCtx))

                val ch = b.bind(PORT).sync().channel()

                print("server started")
                ch.closeFuture().sync()
            } finally {
                bossGroup.shutdownGracefully()
                workerGroup.shutdownGracefully()
            }
        }
    }
}