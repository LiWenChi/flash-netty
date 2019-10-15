package the.flash.protocol.request;

import lombok.Data;
import the.flash.protocol.Packet;

import java.util.List;

import static the.flash.protocol.command.Command.CREATE_GROUP_REQUEST;

/**
 * 创建群组的数据包
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList; //群聊用户列表

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
