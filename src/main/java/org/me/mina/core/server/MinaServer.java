package org.me.mina.core.server;

import org.me.mina.core.config.ServerConfig;

public interface MinaServer {
	public void start(ServerConfig config);
	public void stop();
}
