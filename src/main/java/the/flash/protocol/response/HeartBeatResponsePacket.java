package the.flash.protocol.response;

import the.flash.protocol.Packet;

import static the.flash.protocol.command.Command.HEARTBEAT_RESPONSE;

/**
 * 处理客户端心跳数据的数据包
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
