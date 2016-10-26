package nia.chapter2.echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Listing 2.3 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	/**
	 * @param ctx
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			sb.append("Netty rocks ").append(i + ", ");
		}
		ctx.writeAndFlush(Unpooled.copiedBuffer(sb, CharsetUtil.UTF_8));
	}

	/**
	 * @param ctx
	 * @param in
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
		byte[] b = new byte[in.readableBytes()];
		in.getBytes(0, b, 0, in.readableBytes());
		String s = new String(b);
		//System.out.println("Client received: " + ByteBufUtil.hexDump(in) + ":" + s);
		System.out.println("Client received: " + s);
	}

	/**
	 * @param ctx
	 * @param cause
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
