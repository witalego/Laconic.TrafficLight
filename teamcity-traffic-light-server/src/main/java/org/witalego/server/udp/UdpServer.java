package org.witalego.server.udp;

import org.witalego.config.models.UdpConfigModel;
import org.witalego.contracts.listeners.configs.IUdpConfigListener;
import org.witalego.contracts.logging.ILog;
import org.witalego.contracts.server.IServer;
import org.witalego.server.BuildServerWrapper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class UdpServer implements IServer, IUdpConfigListener
{
    private static final int TimerDelay = 5000;

    private final BuildServerWrapper _buildServerWrapper;
    private final ILog _log;

    private DatagramSocket _socket = null;
    private final Timer _timer = new Timer();
    private TimerTask _timerTask = null;

    private Integer _port = null;
    private InetAddress _inetAddress = null;
    private Integer _period = null;

    public UdpServer(BuildServerWrapper buildServerWrapper, ILog log)
    {
        _buildServerWrapper = buildServerWrapper;
        _log = log;
    }

    public void startServer()
    {
        _log.info("UdpServer.startServer.begin");

        if (isRunning())
        {
            _log.info("UdpServer.startServer: server already started");
            return;
        }

        try
        {
            _socket = new DatagramSocket();
            _socket.setBroadcast(true);

            _timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    send(_buildServerWrapper.getCode());
                }
            };

            _timer.scheduleAtFixedRate(_timerTask, TimerDelay, _period * 1000);
        }
        catch (IOException e)
        {
            _log.error("UdpServer.startServer: error occurred during udp server creation", e);
        }

        _log.info("UdpServer.startServer.end");
    }

    public void stopServer()
    {
        _log.info("UdpServer.stopServer.begin");

        if (!isRunning())
        {
            _log.info("UdpServer.stopServer: server already stopped");
            return;
        }

        _timerTask.cancel();
        _timer.purge();

        _socket.close();
        _socket = null;

        _log.info("UdpServer.stopServer.end");
    }

    public void send(byte code)
    {
        if (!isRunning())
        {
            return;
        }

        try
        {
            //_log.info("UdpServer.send.begin " + code);

            byte[] sendData = new byte[] {0x28, 0x06, code};

            _socket.send(new DatagramPacket(sendData, sendData.length, _inetAddress, _port));

            //_log.info("UdpServer.send.end");
        }
        catch (Exception e)
        {
            _log.error("Error occurred during sending Udp packet", e);
        }
    }

    @Override
    public void changed(UdpConfigModel model)
    {
        _log.info("UdpServer.UdpConfigChanged");

        _log.info("UdpServer.UdpConfigChanged: port was changed from %s to %s", _port, model.port.newValue);
        _port = model.port.newValue;

        try
        {
            _log.info("UdpServer.UdpConfigChanged: ip was changed from %s to %s", _inetAddress, model.ipAddress.newValue);
            _inetAddress = InetAddress.getByName(model.ipAddress.newValue);
        }
        catch (UnknownHostException e)
        {
            _log.error("UdpServer.UdpConfigChanged: error occurred during getting ip address", e);
        }

        if (_period != model.period.newValue)
        {
            _period = model.period.newValue;
            if (isRunning())
            {
                stopServer();
            }

            startServer();
        }
    }

    private Boolean isRunning()
    {
        return _socket != null;
    }
}