package netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author liguoping
 * @description
 * @time 2018/7/20 11:39
 * @version 1.0
 *
 */
public class CustomerServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("customerServerHanler...");
        if(msg instanceof CustomMsg){
            CustomMsg customMsg = (CustomMsg) msg;
            System.out.println("remote address: "+ctx.channel().remoteAddress()+", server recieve: "+customMsg.getBody());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
