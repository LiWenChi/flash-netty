package the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import the.flash.protocol.request.HeartBeatRequestPacket;

import java.util.concurrent.TimeUnit;

/**
 * 心跳定时器
 * 排除非假死状态下，客户端确实未发送数据到服务端
 *
 * 心跳定时器也是在封装response之前，所以使用ChannelInboundHandlerAdapter
 * 当通道被激活时，调用channelActive()方法
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    /**
     * 定时心跳任务
     * @param ctx
     */
    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {

        //ctx.executor() 返回的是当前的 channel 绑定的 NIO 线程
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                //如果客户端与服务端属于连接状态，则发送心跳数据包
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }

        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
