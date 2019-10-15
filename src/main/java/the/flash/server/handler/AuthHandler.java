package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import the.flash.util.LoginUtil;

/**
 * 判断一个用户是否登录很简单，只需要调用一下 `LoginUtil.hasLogin(channel)` 即可，
 * 但是，Netty 的 pipeline 机制帮我们省去了重复添加同一段逻辑的烦恼，
 * 我们只需要在后续所有的指令处理 handler 之前插入一个用户认证 handle
 *
 * `AuthHandler` 继承自 `ChannelInboundHandlerAdapter`，覆盖了 `channelRead()` 方法，表明他可以处理所有类型的数据
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtil.hasLogin(ctx.channel())) {
            //如果未登录，则直接关闭连接
            ctx.channel().close();
        } else {
            //如果已登录，则将数据传输下去

            ctx.pipeline().remove(this);//当已经成功校验用户已登录，则采用热插拔的方式删除此handler
            super.channelRead(ctx, msg);
        }
    }

    /**
     * 当handler被删除，则调用此方法
     *
     * 用来演示验证登录后移除handler
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
