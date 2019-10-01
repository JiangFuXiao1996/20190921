package 网络编程.异步io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

/**
 * Created by 拂晓 on 2019/10/1:17:58
 */
public class AIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        client.connect(new InetSocketAddress("localhost", 8989), null, new CompletionHandler<Void, Object>() {

            @Override
            public void completed(Void result, Object attachment) {
                try {
                    System.out.println("连接服务器成功");
                    ByteBuffer buffer = ByteBuffer.allocate(20);
                    Future<Integer> f = client.read(buffer);
                    f.get();
                    buffer.flip();
                    String str = Charset.defaultCharset().decode(buffer).toString();
                    System.out.println(str);
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });

        Thread.sleep(2000);
        System.out.println("田坤");


    }
}