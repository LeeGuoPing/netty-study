package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 
 * @author liguoping
 * @description TODO
 * @time 2018/7/19 14:10
 * @version 1.0
 *
 */
public class CustomDecoder extends LengthFieldBasedFrameDecoder {

    private int HEAD_SIZE = 6;

    private byte type;

    private byte flag;

    private int length;

    private String body;

    /**
     *
     * @param maxFrameLength  解码时处理数据的最大长度
     * @param lengthFieldOffset 长度字段的起始位置
     * @param lengthFieldLength 长度字段本身的长度
     * @param lengthAdjustment  长度字段的偏移量,如果长度是所有长度,则为负数;只包含具体的内容,则为0.
     * @param initialBytesToStrip  解析时需要跳过的长度
     * @param failFast  超过最大长度的处理策略,为true时,超过最大长度,直接报异常.为false时,读取完整个帧再报错
     */
    public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        System.out.println("decode....");
        if(in == null){
            return null;
        }

        if(in.readableBytes()<HEAD_SIZE){
            throw new Exception("可读信息比头部信息少,Are you fucking kidding me?");
        }

        type = in.readByte();
        flag = in.readByte();
        length = in.readInt();

        System.out.println("type: "+type+", flag: "+flag+" length: "+length);

        if(in.readableBytes()<length){
            throw new Exception("长度小于length, length is "+length);
        }

        ByteBuf byteBuf = in.readBytes(length);
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        body = new String(bytes,"UTF-8");

        CustomMsg customMsg = new CustomMsg();
        customMsg.setType(type);
        customMsg.setFlag(flag);
        customMsg.setLength(length);
        customMsg.setBody(body);
        return customMsg;
    }
}
