package netty.proto;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author liguoping
 * @description TODO
 * @time 2018/7/20 18:20
 * @version 1.0
 *
 */
public class ProtoBufServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RichManProto.RichMan richMan = (RichManProto.RichMan) msg;
        System.out.println(richMan.getName()+" 拥有 "+richMan.getCarsCount()+" 辆车. ");

        List<RichManProto.RichMan.Car> carsList = richMan.getCarsList();

        if(carsList !=null ){
            for (RichManProto.RichMan.Car car: carsList) {
                System.out.println(car.getName());
            }
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
