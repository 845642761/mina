package org.me.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.me.mina.core.config.ServerConfig;
import org.me.mina.core.server.MinaServer;

public class SocketServer implements MinaServer {
	private Logger log = Logger.getLogger(SocketServer.class);
	private IoAcceptor acceptor;

	/**
	 * 启动服务器端口
	 */
	@Override
	public void start(ServerConfig config) {
		log.info("starting socketServer : port is "+config.getServerPort()+"...");
		long start = System.currentTimeMillis();
		// 创建一个非阻塞的server端的Socket
		acceptor = new NioSocketAcceptor();
		// 添加打印日志 过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
		acceptor.setHandler(config.getServerHandler());
		try {
			// 设置socket的最大缓冲值
			acceptor.getSessionConfig().setReadBufferSize(config.getServerBuffer());
			// 设置
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,config.getServerDie());
			// 绑定服务器端端口
			acceptor.bind(new InetSocketAddress(config.getServerPort()));
		} catch (IOException e) {
			log.error("Cannot start server", e);
		}
		long used = System.currentTimeMillis() - start;
		log.info("socketServer start complete : "+used+"ms");
	}

	@Override
	public void stop() {
		log.info("starting stop socketServer...");
		long start = System.currentTimeMillis();
		if (acceptor != null) {
			acceptor.unbind();
			acceptor.dispose();
		}
		long used = System.currentTimeMillis() - start;
		log.info("stop socketServer complete!"+used+"ms");
	}
}
