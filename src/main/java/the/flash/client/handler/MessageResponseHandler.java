package the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.response.MessageResponsePacket;

/**
 * 客户端处理服务端发送过来的消息
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    /**
     *
     * @param ctx
     * @param messageResponsePacket 服务端发送的数据(来自于另一个客户端消息)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {

        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + messageResponsePacket
                .getMessage());
    }
}
