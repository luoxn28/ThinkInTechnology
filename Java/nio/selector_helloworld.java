import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * ²âÊÔÀà
 */
public class Hello {
    public static void main(String[] args) {
        Runnable taskServer = new Runnable() {
            public void run() {
                server();
            }
        };
        Runnable taskClient = new Runnable() {
            public void run() {
                client();
            }
        };

        Thread threadServer = new Thread(taskServer);
        Thread threadClient = new Thread(taskClient);

        threadServer.start();
        threadClient.start();
    }

    /**
     * server
     */
    public static void server() {
        ServerSocketChannel channel = null;

        try {
            // create selector
            Selector selector = Selector.open();

            // create socket channel
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().setReuseAddress(true);
            channel.bind(new InetSocketAddress(8080));
            // register channel to selector
            channel.register(selector, SelectionKey.OP_ACCEPT, 1);

            while (true) {
                if (selector.select() > 0) {
                    Set<SelectionKey> sets = selector.selectedKeys();
                    Iterator<SelectionKey> keys = sets.iterator();

                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();

                        if (key.isAcceptable()) {
                            SocketChannel chn = ((ServerSocketChannel) key.channel()).accept();
                            chn.configureBlocking(false);
                            chn.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        }

                        if (key.isReadable()) {
                            ByteBuffer buff = ByteBuffer.allocate(1024);
                            ByteOutputStream output = new ByteOutputStream();
                            SocketChannel chn = (SocketChannel) key.channel();

                            while (chn.read(buff) != 0) {
                                buff.flip();

                                byte data[] = new byte[buff.remaining()];
                                buff.get(data);
                                output.write(data);
                                buff.clear();
                            }

                            String info = new String(output.getBytes());
                            key.attach(info);
                        }

                        if (key.isWritable()) {
                            Object object = key.attachment();
                            // object is not null after server receive a message from client.
                            if (object != null) {
                                String attach = "server replay: " + object.toString();
                                SocketChannel chn = (SocketChannel) key.channel();
                                chn.write(ByteBuffer.wrap(attach.getBytes()));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (channel != null) {
                try {
                    channel.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * client
     */
    public static void client() {
        SocketChannel channel = null;

        try {
            // create selector
            Selector selector = Selector.open();

            // create channel
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(8080));
            // register channel to selector
            channel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> sets = selector.selectedKeys().iterator();
                    while (sets.hasNext()) {
                        SelectionKey key = sets.next();
                        sets.remove();

                        SocketChannel chn = (SocketChannel) key.channel();
                        if (key.isConnectable()) {
                            chn.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, 1);
                            chn.finishConnect();
                        }

                        if (key.isReadable()) {
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            ByteBuffer buff = ByteBuffer.allocate(1024);

                            while (chn.read(buff) != 0) {
                                buff.flip();

                                byte[] data = new byte[buff.remaining()];
                                buff.get(data);
                                output.write(data);
                                buff.clear();
                            }

                            System.out.println(new String(output.toByteArray()));
                            output.close();
                        }

                        if (key.isWritable()) {
                            chn.write((ByteBuffer.wrap(("client say: hi, luxon28").getBytes())));
                        }
                    }
                }

                Thread.sleep(1000);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (channel != null) {
                try {
                    channel.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
