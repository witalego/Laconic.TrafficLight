package org.witalego.contracts.server;

import org.witalego.contracts.listeners.configs.IConfigListener;

public interface IServer extends IConfigListener
{
    void startServer();
    void stopServer();

    void send(byte code);
}