package org.witalego.contracts.server;

import org.witalego.config.ProtocolType;

public interface IServerFactory
{
    <T extends IServer> T getServer(ProtocolType protocolType);
}