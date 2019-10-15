package the.flash.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台执行指令的接口
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
