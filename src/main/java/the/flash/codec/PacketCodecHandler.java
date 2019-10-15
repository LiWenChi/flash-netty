package the.flash.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import the.flash.protocol.Packet;
import the.flash.protocol.PacketCodec;

import java.util.List;

/**
 * 压缩 handler - 合并编解码器
 * 将编解码放都放到该类中
 * PacketCodecHandler是一个无状态的 handler，因此，同样可以使用单例模式来实现
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {

    }

    /**
     * 解码
     * @param ctx
     * @param byteBuf
     * @param out
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        out.add(PacketCodec.INSTANCE.decode(byteBuf));
    }

    /**
     * 编码
     * @param ctx
     * @param packet
     * @param out
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf, packet);
        out.add(byteBuf);
    }
}
