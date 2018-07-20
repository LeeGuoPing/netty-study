package netty.protocol;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liguoping
 * @version 1.0
 * @description TODO
 * @time 2018/7/20 13:54
 */
public class CustomEncoder extends MessageToByteEncoder<CustomMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CustomMsg msg, ByteBuf out) throws Exception {
        System.out.println("encode");
        System.out.println("encode msg length:"+msg.getLength());

        if (null == msg) {
            throw new Exception("msg is null");
        }

        String body = msg.getBody();
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        out.writeByte(msg.getType());
        out.writeByte(msg.getFlag());
        out.writeInt(bodyBytes.length);
        out.writeBytes(bodyBytes);

    }

}
