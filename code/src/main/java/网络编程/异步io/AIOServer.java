package 网络编程.异步io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by 拂晓 on 2019/10/1:17:57
 */
public class AIOServer {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();

        server.bind(new InetSocketAddress(8989));

        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                try {
                    System.out.println("11111");
                    ByteBuffer buffer = ByteBuffer.wrap("Hello".getBytes());
                    Future<Integer> f = result.write(buffer);
                    f.get();
                    result.close();
                    server.accept(null, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
            }
        });

        System.out.println("main go on");

        while (true) {
            Thread.sleep(1000);
        }
        
    }

}
