package 网络编程.同步非阻塞io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 拂晓 on 2019/10/1:17:07
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel sChannel = ServerSocketChannel.open();
        sChannel.bind(new InetSocketAddress(9000));//绑定9000端口
        sChannel.configureBlocking(false);//设置非阻塞

        Selector selector = Selector.open();//开启选择器

        sChannel.register(selector, SelectionKey.OP_ACCEPT);// 注册感兴趣的事件,有客户端连接

        while (true){
            selector.select();//阻塞
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();

                if(key.isAcceptable()){//当有客户端连接时处理
                    SocketChannel channel = sChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ);//感兴趣事件 准备好读取数据

                }
                if(key.isReadable()){//当有客户端发送数据时,服务器准备去读数据
                    ByteBuffer buffer = ByteBuffer.allocate(20);
                    SocketChannel channel = (SocketChannel)key.channel();
                    channel.read(buffer);
                    buffer.flip();

                    String str = Charset.defaultCharset().decode(buffer).toString();
                    System.out.println(str);

                    if("quit".equals(str)){
                        channel.write(ByteBuffer.wrap("quit".getBytes()));
                        channel.close();
                     }
                    else{
                        str = str.toUpperCase();
                        buffer = ByteBuffer.wrap(str.getBytes());//str --->ByteBuffer
                        channel.write(buffer);
                    }
                }
            }
        }

    }
}