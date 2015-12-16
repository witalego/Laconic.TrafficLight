package org.witalego.server.tcp;

import org.witalego.config.models.TcpConfigModel;
import org.witalego.contracts.listeners.configs.ITcpConfigListener;
import org.witalego.contracts.listeners.server.ITcpClientListener;
import org.witalego.contracts.logging.ILog;
import org.witalego.contracts.server.IServer;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServer implements IServer, ITcpClientListener, ITcpConfigListener
{
    private final ILog _log;

    private ServerSocket _socket = null;
    private Thread _thread = null;
    private final ArrayList<Socket> _clientSockets = new ArrayList<>();

    private Integer _port = null;

    public TcpServer(ILog log)
    {
        _log = log;
    }

    public void startServer()
    {
        if (isRunning())
        {
            _log.info("TcpServer.startServer: server already started");
            return;
        }

        try
        {
            _log.info("TcpServer.startServer: start");

            _log.info("TcpServer.startServer: creating socket");
            _socket = new ServerSocket(_port);

            _log.info("TcpServer.startServer: starting thred");
            _thread = new TcpServerThread(_socket, this, _log);
            _thread.start();

            _log.info("TcpServer.startServer: end");
        }
        catch (IOException e)
        {
            _log.error("Error occurred during Tcp Server creation", e);
        }
    }

    public void stopServer()
    {
        _log.info("TcpServer.stopServer: begin");

        if (!isRunning())
        {
            _log.info("TcpServer.stopServer: server already stopped");
            return;
        }

        _thread.interrupt();

        close(_socket);
        _socket = null;

        _clientSockets.forEach(this::close);
        _clientSockets.clear();

        _log.info("TcpServer.stopServer: end");
    }

    public void send(byte code)
    {
        if (!isRunning() || _clientSockets.isEmpty())
        {
            return;
        }

        _log.info("TcpServer.send code = " + code);

        for (Socket clientSocket : _clientSockets.toArray(new Socket[_clientSockets.size()]))
        {
            try
            {
                OutputStream stream = clientSocket.getOutputStream();
                stream.write(new byte[] {0x28, 0x06, code});
                stream.flush();
            }
            catch (IOException e)
            {
                _log.error("Error occurred during sending code", e);

                _clientSockets.remove(clientSocket);
            }
        }
    }

    @Override
    public void changed(TcpConfigModel model)
    {
        _log.info("TcpServer.changed");

        if (!model.port.newValue.equals(_port))
        {
            _log.info("TcpServer.changed: port was changed from %s to %s", _port, model.port.newValue);

            stopServer();
        }

        _port = model.port.newValue;

        startServer();
    }

    @Override
    public void connected(Socket socket)
    {
        _log.info("TcpServer.connected: client was connected");
        _clientSockets.add(socket);
    }

    private Boolean isRunning()
    {
        return _socket != null;
    }

    private void close(Closeable closeable)
    {
        try
        {
            closeable.close();
        }
        catch (IOException e)
        {
            _log.error("TcpServer.close: error occurred during closing socket", e);
        }
    }
}