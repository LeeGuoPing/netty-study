package netty.protocol;
/**
 * 
 * @author liguoping
 * @description 自定义协议
 * @time 2018/7/19 14:00
 * @version 1.0
 *
 */
public class CustomMsg {

    private byte type;  // 类型  系统编号 0xAB 表示A系统，0xBC 表示B系统

    private byte flag;  // 信息标识   0xAB 表示心跳包    0xBC 表示超时包  0xCD 业务信息包

    private int length;  // 长度

    private String body;  // 主题信息

    public CustomMsg(){

    }

    public CustomMsg(byte type, byte flag, int length, String body) {
        this.type = type;
        this.flag = flag;
        this.length = length;
        this.body = body;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
