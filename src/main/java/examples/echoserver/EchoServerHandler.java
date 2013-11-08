package examples.echoserver;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override    
    public void channelRead(ChannelHandlerContext context, Object message) {
	System.out.println("Server has received: " + message);
	context.write(message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
	context.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
	cause.printStackTrace();
	context.close();
    }
}
