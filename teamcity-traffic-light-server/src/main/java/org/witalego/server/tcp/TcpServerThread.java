package org.witalego.server.tcp;

import org.witalego.contracts.listeners.server.ITcpClientListener;
import org.witalego.contracts.logging.ILog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerThread extends Thread
{
    private ServerSocket _socket;
    private ITcpClientListener _listener;
    private ILog _log;

    public TcpServerThread(ServerSocket socket, ITcpClientListener listener, ILog log)
    {
        _socket = socket;
        _listener = listener;
        _log = log;
    }

    @Override
    public void run()
    {
        _log.info("TcpServerThread.run: start");

        while (!isInterrupted())
        {
            try
            {
                Socket client = _socket.accept();

                _log.info("TcpServerThread.run: client was connected");
                if (_listener != null)
                {
                    _log.info("TcpServerThread.run: notify listener");
                    _listener.connected(client);
                }
                else
                {
                    _log.info("TcpServerThread.run: no listener");
                }
            }
            catch (IOException e)
            {
                _log.error("TcpServerThread.run: error occurred during waiting clients", e);
            }
        }
    }
}