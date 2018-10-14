package flash.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author liguoping
 * @version 1.0
 * @description TODO
 * @time 2018/10/9 18:32
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;  // 最大重连次数

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
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    /**
     * 连接重试逻辑
     *
     * @param bootStrap
     * @param host
     * @param port
     */
    private static void connect(Bootstrap bootStrap, String host, int port, int retry) {
        bootStrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.out.println("连接次数已经用尽,放弃连接.");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                System.out.println(new Date() + " : " + "重试第: " + order + "次");
                int delay = 1 << order; // 重试延迟时间
                bootStrap.config().group().schedule(() -> connect(bootStrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
        return;
    }
}
