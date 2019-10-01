package 网络编程.同步非阻塞io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 拂晓 on 2019/10/1:17:08
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        final SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        final Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT);

        channel.connect(new InetSocketAddress("localhost",9000));

        //向服务器写数据
        Thread t = new Thread(){
            @Override
            public void run() {

                try {
                    while (!channel.isConnected()){}

                    for (int i = 0 ; i<=30;i++){
                        String str = "hello"+i;
                        ByteBuffer buffer =  ByteBuffer.wrap(str.getBytes());
                        channel.write(buffer);
                        Thread.sleep(200);
                    }
                    ByteBuffer buffer = ByteBuffer.wrap("quit".getBytes());
                    channel.write(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();


        while (true){
            selector.select();
            if(!selector.isOpen()) break;
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()){
                 SelectionKey key = it.next();
                 it.remove();

                 if(key.isConnectable()){
                    if(!channel.isConnected()){
                        channel.finishConnect();//如果连接还没完成,则完成连接
                    }
                    channel.register(selector,SelectionKey.OP_READ);
                 }
                 if(key.isReadable()){
                     ByteBuffer buffer = ByteBuffer.allocate(20);
                     channel.read(buffer);
                     buffer.flip();
                     String str = Charset.defaultCharset().decode(buffer).toString();

                     if("quit".equals(str)){
                         channel.close();
                         selector.close();
                         return;
                     }
                     System.out.println(str);
                 }
            }
        }

    }
}