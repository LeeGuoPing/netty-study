package netty.protocol;

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


    public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }
}
