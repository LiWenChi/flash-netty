package the.flash.protocol.response;

import lombok.Data;
import the.flash.protocol.Packet;

import static the.flash.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * 服务端发送给客户端的msg
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId; //消息来源userId

    private String fromUserName; //消息来源userName

    private String message; //消息内容

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
