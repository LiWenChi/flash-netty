package the.flash.attribute;

import io.netty.util.AttributeKey;

/**
 * 定义在Channel中绑定的属性
 */
public interface Attributes {

    //登录成功后的标示位
    //newInstance("login") 定义AttributeKey的名称
    // <Boolean>表示AttributeMap中key为LOGIN的value值类型为Boolean
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
