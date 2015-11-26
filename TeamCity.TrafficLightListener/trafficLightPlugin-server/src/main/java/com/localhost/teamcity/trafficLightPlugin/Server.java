package com.localhost.teamcity.trafficLightPlugin;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread
{
    private ServerSocket _serverSocket = null;
    private ArrayList<Socket> _clientSockets = new ArrayList<Socket>();
    private static Logger Log = Loggers.SERVER;

    public Server(int port) throws IOException
    {
        Log.info("TrafficLightServer [Server] - constructor");

        _serverSocket = new ServerSocket(port);
    }

    @Override
    public void run()
    {
        Log.info("TrafficLightServer [Server] - run");

        while (true)
        {
            try
            {
                _clientSockets.add(_serverSocket.accept());

                Log.info("TrafficLightServer [Server] - run.connected");
            }
            catch (IOException e)
            {
                Log.info("TrafficLightServer [Server] - run.exception: " + e.getMessage());

                e.printStackTrace();
            }
        }
    }

    public void send(byte code)
    {
        Log.info("TrafficLightServer [Server] - send " + code);

        for (Socket clientSocket : _clientSockets.toArray(new Socket[_clientSockets.size()]))
        {
            try
            {
                OutputStream stream = clientSocket.getOutputStream();
                stream.write(new byte[]{0x28, 0x06, code});
                stream.flush();
                //stream.close();
            }
            catch (IOException e)
            {
                Log.info("TrafficLightServer [Server] - send.exception: " + e.getMessage());

                _clientSockets.remove(clientSocket);

                e.printStackTrace();
            }
        }
    }
}