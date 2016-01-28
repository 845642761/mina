package org.me.mina.core.config;

import org.apache.mina.core.service.IoHandlerAdapter;

public class ServerConfig {
	private int serverBuffer = 10240000;//服务器接收缓冲区大小
	private int serverPort = 10000;//服务器绑定端口;
	private int serverDie = 10000;//服务器idle时间，单位 s
	private IoHandlerAdapter serverHandler;//业务逻辑处理器;
	
	public int getServerBuffer() {
		return serverBuffer;
	}
	public void setServerBuffer(int serverBuffer) {
		this.serverBuffer = serverBuffer;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public int getServerDie() {
		return serverDie;
	}
	public void setServerDie(int serverDie) {
		this.serverDie = serverDie;
	}
	public IoHandlerAdapter getServerHandler() {
		return serverHandler;
	}
	public void setServerHandler(IoHandlerAdapter serverHandler) {
		this.serverHandler = serverHandler;
	}
}
