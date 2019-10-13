package the.flash.protocol.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import the.flash.serialize.Serializer;
import the.flash.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static the.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 *
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    //定义字节数值与数据包的映射关系
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    //定义字节数值与序列化方式的映射关系
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    /**
     * 数据编码
     * 将序列化后的数据编码到通讯协议中的二进制数据包中
     * @param packet
     * @return
     */
    public ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        //  调用Netty的ByteBuf分配器来创建ByteBuf
        //  `ioBuffer()`方法会返回适配 io 读写相关的内存，它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2. 序列化 java 对象
        //  将Java序列化为二进制数据包
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        //  根据我们实现定义好的通讯协议将二进制数据写入到ByteBuf
        //  通讯协议：4个字节的魔数+1个字节的版本号+序列化方式+1个字节的处理逻辑的指令+4个字节表示数据长度+数据内容
        //使用write()方法写索引会更新到最新的写位置
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    /**
     * 解码
     * 解析Java对象的过程
     * 根据
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);


        //调用 `ByteBuf` 的 API 分别拿到序列化算法标识、指令、数据包的长度
        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);//从byteBuf中读出length字节长度的数据到byttes中

        //根据字节类型的key得到指令类型(对应什么样的逻辑操作)
        Class<? extends Packet> requestType = getRequestType(command);
        //根据字节类型的key得到序列化的方式
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    /**
     * 根据映射关系得到序列化方法
     * @param serializeAlgorithm
     * @return
     */
    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    /**
     * 根据映射关系得到反序列后的具体目标类
     * @param command
     * @return
     */
    private Class<? extends Packet> getRequestType(byte command) {
        //从map中得到对应的类
        return packetTypeMap.get(command);
    }
}
