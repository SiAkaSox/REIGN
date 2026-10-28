package io.ruin.update.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.ruin.update.utils.ServerWrapper;

public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if(!cause.getStackTrace()[0].getMethodName().equals("read0"))
            ServerWrapper.logError("Netty issue", cause);
        ctx.close();
    }

}