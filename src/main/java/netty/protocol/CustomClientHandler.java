package netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author liguoping
 * @description TODO
 * @time 2018/7/20 13:52
 * @version 1.0
 *
 */
public class CustomClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("customClientHandler...");

        // 此处的length 实际上没什么作用,是在编码类中限制的,必须要要保持长度
        CustomMsg customMsg = new CustomMsg((byte)0xAB, (byte)0xCD, "Hello,Netty".length(), "Hello,Netty");
        ctx.writeAndFlush(customMsg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
