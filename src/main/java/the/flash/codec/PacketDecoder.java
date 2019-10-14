package the.flash.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import the.flash.protocol.PacketCodeC;

import java.util.List;

/**
 *
 * 通常情况下，无论我们是在客户端还是服务端，当我们收到数据之后，首先要做的事情就是把二进制数据转换到我们的一个 Java 对象，
 * 所以 Netty 很贴心地写了一个父类 : ByteToMessageDecoder
 */
public class PacketDecoder extends ByteToMessageDecoder {

    /**
     * 将二进制解码成Java对象
     *
     * 另外，值得注意的一点，对于 Netty 里面的 ByteBuf，我们使用 `4.1.6.Final` 版本，默认情况下用的是堆外内存
     * 这里我们使用 `ByteToMessageDecoder`，Netty 会自动进行内存的释放，我们不用操心太多的内存管理方面的逻辑
     * @param ctx
     * @param in 输入的就是byteBUffer字段
     * @param out 添加解码后的结果对象
     *
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}
