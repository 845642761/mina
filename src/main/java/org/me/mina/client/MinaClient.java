package org.me.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient extends IoHandlerAdapter{
	private static Logger log = Logger.getLogger(MinaClient.class);
	public static final int CONNECT_TIMEOUT = 30000;
	private String host;
	private int port;
	private SocketConnector connector;
	private IoSession session;
	
	public MinaClient(String host, int port) {
		this.host = host;
		this.port = port;
		connector = new NioSocketConnector();
		connector.getFilterChain().addLast("codec",	new ProtocolCodecFilter(new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
		connector.setHandler(this);
	}
	
	public boolean isConnected() {
		return (session != null && session.isConnected());
	}

	public boolean connect() {
		log.info("starting to connect server : "+host+":"+port);
		long start = System.currentTimeMillis();
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(host, port));
		connectFuture.awaitUninterruptibly(CONNECT_TIMEOUT);
		try {
			session = connectFuture.getSession();
		} catch (RuntimeIoException e) {
			e.printStackTrace();
			log.error(e);
			log.error("can't connect the server...");
			return false;
		}
		
		boolean isConnected = isConnected();
		long used = System.currentTimeMillis() - start;
		if(isConnected){
			log.info("connected the server : "+used+"ms");
		}else {
			log.error("can't connect the server : "+used+"ms");
		}
		return isConnected;
	}

	public void close() {
		long start = System.currentTimeMillis();
		log.info("starting to disconnect server...");
		if (session != null) {
			session.close(true).awaitUninterruptibly(CONNECT_TIMEOUT);
			session = null;
		}
		long used = System.currentTimeMillis() - start;
		log.info("disconnect server complete : "+used+"ms");
	}

	public void send(String msg) {
		log.debug("starting send a message : " + msg);
		long start = System.currentTimeMillis();
		try {
			session.write(msg);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		long used = System.currentTimeMillis() - start;
		log.debug("send message complete : "+used+"ms");
	}
}
