package the.flash.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import the.flash.protocol.Packet;

import static the.flash.protocol.command.Command.MESSAGE_REQUEST;



/**
 * 客户端发送给服务端的消息的数据包格式
 */

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId; //发送给哪个用户
    private String message; //具体内容

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
