package the.flash.protocol.command;

/**
 * 定义业务逻辑指令
 * 表示各种消息的具体业务含义
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3; //客户端发送消息到服务端指令

    Byte MESSAGE_RESPONSE = 4; //服务端发送消息到客户端指令
}
