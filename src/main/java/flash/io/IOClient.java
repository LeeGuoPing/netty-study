package flash.io;

import java.net.Socket;

/**
 * @author liguoping
 * @version 1.0
 * @description 传统IO编程中的客户端实现
 * @time 2018/9/29 18:10
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {

            try {
                Socket socket = new Socket("127.0.0.1", 8000);

                while (true) {
                    socket.getOutputStream().write("hello world!".getBytes());

                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }).start();


    }
}
