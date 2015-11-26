package com.localhost.teamcity.trafficLightPlugin;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SRunningBuild;

import java.io.IOException;
import java.util.List;

public class TrafficLightServer extends BuildServerAdapter
{
    private static final int Port = 2806;
    private Server _server;
    private SBuildServer _buildServer;
    private static Logger Log = Loggers.SERVER;

    public TrafficLightServer(SBuildServer sBuildServer)
    {
        _buildServer = sBuildServer;

        Log.info("TrafficLightServer - constructor");
    }

    public void register() throws IOException
    {
        Log.info("TrafficLightServer - register.begin");

        _server = new Server(Port);
        _server.start();

        _buildServer.addListener(this);

        Log.info("TrafficLightServer - register.end");
    }

    public void buildStarted(SRunningBuild build)
    {
        Log.info("TrafficLightServer - buildStarted");

        updateBroadcast();
    }

    public void buildFinished(SRunningBuild build)
    {
        Log.info("TrafficLightServer - buildFinished");

        updateBroadcast();
    }

    private void updateBroadcast()
    {
        Status status = getWorstStatus();
        LightMode runningCode = getRunningCode();

        LightMode code = status.isSuccessful()
                ? LightMode.Green
                : LightMode.Red;

        _server.send((byte)(code.getValue() | runningCode.getValue()));
    }

    private Status getWorstStatus()
    {
        List<SBuildType> allBuildTypes = _buildServer.getProjectManager().getAllBuildTypes();
        return _buildServer.getStatusProvider().getWorstBuildTypesStatus(allBuildTypes);
    }

    private LightMode getRunningCode()
    {
        return _buildServer.getNumberOfRunningBuilds() > 0
                ? LightMode.Yellow
                : LightMode.None;
    }
}