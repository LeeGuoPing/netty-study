package flash.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author liguoping
 * @description TODO
 * @time 2018/10/9 18:32
 * @version 1.0
 *
 */
public class NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });
        bootstrap.connect("127.0.0.1",8000).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("建立连接成功!");
            }else{
                System.out.println("建立连接失败!");
            }
        });

    }
}
