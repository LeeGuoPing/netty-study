package flash.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author liguoping
 * @version 1.0
 * @description 传统IO编程中的服务端实现
 * @time 2018/9/29 18:19
 */
public class IOServer {

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(8000);

        // (1) 接受新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket accept = serverSocket.accept();
                    // (2) 每一个新的连接都创建一个线程,负责读取数据
                    new Thread(() -> {
                        try {
                            InputStream inputStream = accept.getInputStream();
                            int length;
                            byte[] bytes = new byte[1024];
                            while ((length = inputStream.read(bytes)) != -1) {
                                System.out.println(new String(bytes, 0, length));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
