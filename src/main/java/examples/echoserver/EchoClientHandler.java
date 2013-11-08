package examples.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext context) {
	context.writeAndFlush(Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext context, ByteBuf in) {
	System.out.println("Client Received: "+ ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
	cause.printStackTrace();
	context.close();
    }
}
