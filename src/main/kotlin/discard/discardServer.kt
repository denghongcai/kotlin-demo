package discard

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

/**
 * Discards any incoming data.
 */
class DiscardServer(port:Int) {
    private var port:Int = 0
    init{
        this.port = port
    }
    @Throws(Exception::class)
    fun run() {
        val bossGroup = NioEventLoopGroup() // (1)
        val workerGroup = NioEventLoopGroup()
        try
        {
            val b = ServerBootstrap() // (2)
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel::class.java) // (3)
                    .childHandler(object:ChannelInitializer<SocketChannel>() { // (4)
                        @Throws(Exception::class)
                        override fun initChannel(ch:SocketChannel) {
                            ch.pipeline().addLast(DiscardServerHandler())
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // (6)
            // Bind and start to accept incoming connections.
            val f = b.bind(port).sync() // (7)
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync()
        }
        finally
        {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }
    companion object {
        @Throws(Exception::class)
        @JvmStatic fun main(args:Array<String>) {
            val port:Int
            if (args.size > 0)
            {
                port = Integer.parseInt(args[0])
            }
            else
            {
                port = 8080
            }
            DiscardServer(port).run()
        }
    }
}