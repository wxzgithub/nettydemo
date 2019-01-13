package com.wxz.learn.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        byte[] b = new byte[m.readableBytes()];
        m.readBytes(b);
        String str = new String(b, "UTF-8");
        System.out.println(str);
        String s = "Hello , netty, i am server.";
        ByteBuf code = ctx.alloc().buffer(4 * s.length());
        code.writeBytes(s.getBytes());
        ctx.writeAndFlush(code);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server:channelReadComplete...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
