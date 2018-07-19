package netty.server.tcp.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import netty.server.ServerHanlder;

public class TCPServer {
    private int port;

    public TCPServer(int port){
        this.port = port;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new LineBasedFrameDecoder(2048));  // 以换行符作为结束的标志
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(3)); // 定长数据帧的解码器
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TCPServerHanlder());
                        }
                    }).option(ChannelOption.SO_BACKLOG,128)
                      .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture future = sbs.bind(port).sync();

            System.out.println("server start listen at "+port);

            future.channel().closeFuture();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port;
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 1001;
        }
        new TCPServer(port).start();
    }
}
