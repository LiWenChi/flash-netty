package the.flash.serialize.impl;

import com.alibaba.fastjson.JSON;
import the.flash.serialize.Serializer;
import the.flash.serialize.SerializerAlogrithm;

/**
 * 序列化方法实现类：JSON序列化方法
 * 使用最简单的 json 序列化方式，使用阿里巴巴的作为序列化框架
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
