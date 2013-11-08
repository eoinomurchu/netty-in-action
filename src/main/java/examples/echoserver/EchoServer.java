package examples.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
	this.port = port;
    }

    public void start() throws Exception {
	EventLoopGroup group = new NioEventLoopGroup();
	try {
	    ServerBootstrap b = new ServerBootstrap();
	    b.group(group)
		.channel(NioServerSocketChannel.class)
		.localAddress(new InetSocketAddress(port))
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			    public void initChannel(SocketChannel channel) throws Exception {
			    channel.pipeline().addLast(new EchoServerHandler());
			}
		    });

	    ChannelFuture f = b.bind().sync();
	    System.out.println(EchoServer.class.getName() + " started and listening on " + f.channel().localAddress());
	    f.channel().closeFuture().sync();
	}
	finally {
	    group.shutdownGracefully().sync();
	}
    }
    
    public static void main(String[] args) throws Exception {
	if (args.length != 1)
	    System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
	else {
	    int port = Integer.parseInt(args[0]);
	    new EchoServer(port).start();
	}
    }

}
