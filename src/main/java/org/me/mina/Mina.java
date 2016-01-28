package org.me.mina;

import java.io.IOException;

import org.me.mina.core.config.ServerConfig;
import org.me.mina.server.SocketServer;
import org.me.mina.server.handler.ServerHandler;

public class Mina {
	public static void main(String[] args) {
		Mina mina = new Mina();
		SocketServer ss = mina.startSocketServer();
		
		byte[] byteArray = new byte[1024];
		while (true) {
			try {
				System.in.read(byteArray);
				String cmd = new String(byteArray);
				if (cmd != null && cmd.startsWith("stop")) {
					ss.stop();
					System.exit(0);//系统退出
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * 启动一个SocketServer()
	 * @author: chengbo
	 * @date: 2016年1月28日 16:23:34
	 */
	private SocketServer startSocketServer() {
		SocketServer ss = new SocketServer();
		ServerConfig socketServerConfig = new ServerConfig();
		socketServerConfig.setServerHandler(new ServerHandler());
		ss.start(socketServerConfig);
		return ss;
	}
}
