package the.flash.protocol.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 定义服务端和客户端通讯过程中的Java对象，即通讯传输的数据包
 *
 * 所有的客户端指令操作都是通过指令数据包传输的
 */
@Data //lombok提供，用于自动产生get/set方法
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    /**
     * 所有的指令数据包都必须实现这个方法，这样我们就可以知道某种指令的含义
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
