package the.flash.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import the.flash.attribute.Attributes;

/**
 * Login的相关工具类
 */
public class LoginUtil {

    /**
     * 在Channel中设置login的Attribute
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断login的标记属性是否存在
     * @param channel
     * @return 如果未设置该属性则返回null
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
