package io.ruin.network.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.ruin.utility.IPAddress;
import io.ruin.utility.ServerWrapper;

public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //cause.printStackTrace();
        /** Only occurs when player closes client. **/
        ctx.close();
    }

}