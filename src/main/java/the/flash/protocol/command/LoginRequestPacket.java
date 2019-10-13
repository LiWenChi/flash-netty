package the.flash.protocol.command;

import lombok.Data;

import static the.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 * 定义客户端登录请求的数据包
 */
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
