package the.flash.util;

import io.netty.channel.Channel;
import the.flash.attribute.Attributes;
import the.flash.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session相关工具类
 */
public class SessionUtil {

    //维护userId和Channel的映射关系
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 在chanel的Attribute中绑定一个session;
     * 维持一个useId -> channel 的映射 map
     *
     * @param session 用户信息和消息数据
     * @param channel 该用于所对应的channel
     */
    public static void bindSession(Session session, Channel channel) {
        //session中维持了一个 useId -> channel 的映射 map
        userIdChannelMap.put(session.getUserId(), channel);
        //给channel添加一个session数据，存储用户的session信息，方便获取用户相关信息
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 用户下线之后，我们需要在内存里面自动删除userId到channel的映射关系，这是通过调用
     * @param channel
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判断用户是否的登录，
     * 只需要判断当前用户的channel中是否包含session信息
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
