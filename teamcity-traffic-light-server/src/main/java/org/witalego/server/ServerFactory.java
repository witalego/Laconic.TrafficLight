package org.witalego.server;

import org.witalego.config.ProtocolType;
import org.witalego.contracts.IConfigChangeListener;
import org.witalego.contracts.server.IServer;
import org.witalego.contracts.server.IServerFactory;

import java.util.Map;

public class ServerFactory implements IServerFactory
{
    private Map<ProtocolType, IServer> _servers;
    private IConfigChangeListener _configChangeListener;

    public ServerFactory(Map<ProtocolType, IServer> servers, IConfigChangeListener configChangeListener)
    {
        _servers = servers;
        _configChangeListener = configChangeListener;
    }

    @Override
    public <T extends IServer> T getServer(ProtocolType protocolType)
    {
        T server = (T)_servers.get(protocolType);

        _configChangeListener.register(server);

        return server;
    }
}