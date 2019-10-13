package the.flash.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chao.yu
 * chao.yu@dianping.com
 * @date 2018/08/04 06:21.
 * 在pipline中定义的逻辑处理器，用来读取客户端的数据
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 在接收到客户端发来的数据之后被回调
     * @param ctx
     * @param msg 从客户端发送过来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //`msg` 参数指的就是 Netty 里面数据读写的载体
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        // 回复数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = getByteBuf(ctx);
        //调用 `writeAndFlush()` 方法将数据写出去，发送给客户端
        ctx.channel().writeAndFlush(out);
    }

    /**
     * 定义ByteBuf存储数据
     * Netty 里面数据是以 ByteBuf 为单位的， 所有需要被传输的数据都必须塞到一个 ByteBuf
     * @param ctx
     * @return
     */
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
