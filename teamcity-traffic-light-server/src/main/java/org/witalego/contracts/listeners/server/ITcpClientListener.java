package org.witalego.contracts.listeners.server;

import java.net.Socket;

public interface ITcpClientListener
{
    void connected(Socket socket);
}