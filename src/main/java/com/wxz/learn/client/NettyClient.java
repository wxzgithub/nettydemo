package com.wxz.learn.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NettyClient {

    public void startClient(String host, int port) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ClientChannelHandler());
            ChannelFuture c = b.connect(host, port).sync();
            Thread.sleep(5000);
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
            String str = "i am clinet... " + date;
            ByteBuf buf = Unpooled.buffer(str.length());
            buf.writeBytes(str.getBytes());
            c.channel().writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
            c.channel().closeFuture().sync();
            System.out.println("client close.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    private class ClientChannelHandler extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyClientHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        String s = "127.0.0.1";
        new NettyClient().startClient(s, port);
    }
}
