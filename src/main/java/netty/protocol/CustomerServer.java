package netty.protocol;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liguoping
 * @version 1.0
 * @description TODO
 * @time 2018/7/20 11:33
 */
public class CustomerServer {

    private int maxFrameLength = 1024 * 1024;

    private int lengthFieldOffset = 2;

    private int lengthFieldLength = 4;

    private int lengthAdjustment = 0;

    private int initialBytesToStrip = 0;

    private int port;

    public CustomerServer(int port) {
        this.port = port;
    }

    public void start() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new CustomDecoder(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, false));
                            pipeline.addLast(new CustomerServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("Server start listen at " + port);
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) {
        CustomerServer customerServer = new CustomerServer(8080);
        customerServer.start();
    }
}
