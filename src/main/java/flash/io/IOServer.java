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

        while (true) {
            new Thread(() ->
            {
                try {
                    Socket accept = serverSocket.accept();
                    InputStream inputStream = accept.getInputStream();
                    int length = 0;
                    byte[] bytes = new byte[1024];
                    while ((length = inputStream.read(bytes)) != -1) {
                        System.out.println(new String(bytes,0,length));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
    }
}
