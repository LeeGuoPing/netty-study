package netty.client.tcp.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author liguoping
 * @description TCP粘包,拆包体现 可以具象化理解TCP为一个搬运能力有限的搬运工,
 *                  如果货物过多,需要分多次搬运(拆包),接收端端则需要粘包;
 *                  如果货物过少,需要把多次的货物合并在一起(粘包),接收端需要拆包.
 * @time 2018/7/18 17:32
 * @version 1.0
 *
 */
public class TCPClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] req;

    private byte[] shortReq;

    public TCPClientHandler(){
        req = ("In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. His book w"
                + "ill give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the process "
                + "of configuring and connecting all of Netty’s components to bring your learned about threading models in ge"
                + "neral and Netty’s threading model in particular, whose performance and consistency advantages we discuss"
                + "ed in detail In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. Hi"
                + "s book will give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the"
                + " process of configuring and connecting all of Netty’s components to bring your learned about threading "
                + "models in general and Netty’s threading model in particular, whose performance and consistency advantag"
                + "es we discussed in detailIn this chapter you general, we recommend Java Concurrency in Practice by Bri"
                + "an Goetz. His book will give We’ve reached an exciting point—in the next chapter;the counter is: 1 2222"
                + "sdsa ddasd asdsadas dsadasdas"+System.getProperty("line.separator")).getBytes();

        shortReq = "123".getBytes();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       /* ByteBuf buffer = null;
        buffer = Unpooled.buffer(req.length);
        buffer.writeBytes(req);
        ctx.writeAndFlush(buffer);

        // 二次写入
        buffer = Unpooled.buffer(req.length);
        buffer.writeBytes(req);
        ctx.writeAndFlush(buffer);*/

        for (int i = 0; i <100 ; i++) {
            ByteBuf buffer = null;
            buffer = Unpooled.buffer(shortReq.length);
            buffer.writeBytes(shortReq);
            ctx.writeAndFlush(buffer);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("tcpclient read msg: "+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
