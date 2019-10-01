package 网络编程.同步阻塞io;

import java.io.*;
import java.net.Socket;

/**
 * Created by 拂晓 on 2019/10/1:13:03
 */

//同步阻塞io
public class BioClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost",9000);
        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();

        PrintStream out = new PrintStream(os);
        InputStreamReader ir = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(ir);

         for (int i = 0 ; i<=30; i++){
             out.println("hello"+i);
             out.flush();
             String s1 = in.readLine();
             System.out.println(s1);
         }
         s.close();
    }
}