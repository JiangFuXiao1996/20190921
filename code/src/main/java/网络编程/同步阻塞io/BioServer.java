package 网络编程.同步阻塞io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 拂晓 on 2019/10/1:12:57
 */

public class BioServer {
    public static void main(String[] args) throws IOException {
        //绑定9000端口
        ServerSocket ss = new ServerSocket(9000);
        Socket s = ss.accept();//监听 等待客户端连接

        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();

        PrintWriter out = new PrintWriter(os);
        InputStreamReader ir = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(ir);

        for (int i = 0 ; i<=30;i++){
            final String line = in.readLine();

            final String s1 = line.toUpperCase();

            out.println(s1);
            out.flush();
        }

        s.close();
        ss.close();

    }
}