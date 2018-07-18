package netty.client.tcp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import netty.client.ClientHandler;
import netty.server.tcp.server.TCPServerHanlder;

public class TCPClient {

    private static final String host = "127.0.0.1";

    private static final int port = 1001;

    private static final int size = 256;

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast("decode",new StringDecoder());
                            channelPipeline.addLast("encode",new StringEncoder());
                            channelPipeline.addLast(new TCPClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            /*future.channel().writeAndFlush("listen me");
            future.channel().closeFuture().sync();*/
        } finally {
            group.shutdownGracefully();
        }
    }

}
