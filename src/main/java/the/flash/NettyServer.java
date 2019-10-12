package the.flash;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * 服务端启动流程
 * - 本文中，我们首先学习了 Netty 服务端启动的流程，一句话来说就是：创建一个引导类，然后给他指定线程模型，IO模型，连接读写处理逻辑，绑定端口之后，服务端就启动起来了。
 * - 然后，我们学习到 bind 方法是异步的，我们可以通过这个异步机制来实现端口递增绑定。
 * - 最后呢，我们讨论了 Netty 服务端启动额外的参数，主要包括给服务端 Channel 或者客户端 Channel 设置属性值，设置底层 TCP 参数。
 */
public class NettyServer {

    private static final int BEGIN_PORT = 8000;

    /**
     * 要启动一个Netty服务端，必须要指定三类属性，分别是
     *      线程模型 NioEventLoopGroup NioEventLoopGroup
     *      IO 模型 NioServerSocketChannel.class
     *      连接读写处理逻辑 childHandler()
     * @param args
     */
    public static void main(String[] args) {

        //`bossGroup`表示监听端口，accept 新连接的线程组
        //`workerGroup`表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建了一个引导类 `ServerBootstrap`，这个类将引导我们进行服务端的启动工作，直接new出来开搞
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");
        serverBootstrap
                //`.group(bossGroup, workerGroup)`给引导类配置两大线程组，这个引导类的线程模型也就定型了
                .group(boosGroup, workerGroup)
                //`.channel(NioServerSocketChannel.class)`来指定 IO 模型
                .channel(NioServerSocketChannel.class)
                //`attr()`方法可以给服务端的 channel，也就是`NioServerSocketChannel`指定一些自定义属性，然后我们可以通过`channel.attr()`取出这个属性
                //其实说白了就是给`NioServerSocketChannel`维护一个map而已
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                //`childAttr`可以给每一条连接指定自定义属性，然后后续我们可以通过`channel.attr()`取出该属性
                .childAttr(clientKey, "clientValue")
                //表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                //`childOption()`可以给每条连接设置一些TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true) //表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.TCP_NODELAY, true) //`表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                //`childHandler()`方法，给这个引导类创建一个`ChannelInitializer`，这里主要就是定义后续每条连接的数据读写，业务处理逻辑
                //泛型参数`NioSocketChannel`，这个类呢，就是 Netty 对 NIO 类型的连接的抽象
                //`NioServerSocketChannel`和`NioSocketChannel`的概念可以和 BIO 编程模型中的`ServerSocket`以及`Socket`两个概念对应上
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println(ch.attr(clientKey).get());
                    }
                });


        bind(serverBootstrap, BEGIN_PORT);
    }

    /**
     * 自动绑定递增端口
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
