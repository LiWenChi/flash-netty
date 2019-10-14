package the.flash.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import the.flash.protocol.Packet;
import the.flash.protocol.PacketCodeC;

/**
 * 把响应对象编码成 ByteBuf
 * 泛型参数 `Packet` 表示这个类的作用是实现 `Packet` 类型对象到二进制的转换
 *
 * Netty 提供了一个特殊的 channelHandler 来专门处理编码逻辑，
 * 我们不需要每一次将响应写到对端的时候调用一次编码逻辑进行编码，也不需要自行创建 ByteBuf，这个类叫做 `MessageToByteEncoder`
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    /**
     * 将packet编码为ByteBuf
     * 这个方法里面要做的事情就是把 Java 对象里面的字段写到 ByteBuf，我们不再需要自行去分配 ByteBuf
     * @param ctx
     * @param packet
     * @param out
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodeC.INSTANCE.encode(out, packet);
    }
}
