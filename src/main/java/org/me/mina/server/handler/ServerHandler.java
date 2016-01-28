package org.me.mina.server.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {
	private Logger log = Logger.getLogger(ServerHandler.class);
	
	/**
	 * 功能说明：有异常发生时被触发
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)	throws Exception {
		log.error("Server socket execption", cause);
	}

	/**
	 * 功能说明：有消息到达时被触发，message代表接收到的消息
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		log.info("[==== receive a message is:"+message.toString()+" ====]");
	}

	/**
	 * 功能说明：当创建一个新连接时被触发，即当开始一个新的Session时被触发。
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		log.info("server session created: ip is:"+session.getRemoteAddress().toString());
	}
	
	/**
	 * 功能说明：断开连接时被触发
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		log.info("server session stop: ip is:"+session.getRemoteAddress().toString());
	}
}
