package com.wxz.learn.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "hello, i am client";
        ByteBuf encode = ctx.alloc().buffer(4 * msg.length());
        encode.writeBytes(msg.getBytes());
        ctx.writeAndFlush(encode);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf s = (ByteBuf) msg;
        byte[] b = new byte[s.readableBytes()];
        s.readBytes(b);
        String str = new String(b, "UTF-8");
        System.out.println(str);
       /* String s1 = "Hello,netty,i am Client.";
        ByteBuf code = ctx.alloc().buffer(4 * s1.length());
        code.writeBytes(s1.getBytes());
        ctx.writeAndFlush(code);*/
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:channelReadComplete...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
