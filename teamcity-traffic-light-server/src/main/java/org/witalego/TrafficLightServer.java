package org.witalego;

import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SRunningBuild;
import org.witalego.contracts.logging.ILog;
import org.witalego.server.Server;

public class TrafficLightServer extends BuildServerAdapter
{
    private SBuildServer _buildServer;
    private Server _server;
    private ILog _log;

    public TrafficLightServer(SBuildServer sBuildServer, Server server, ILog log)
    {
        _buildServer = sBuildServer;
        _server = server;
        _log = log;
    }

    public void register()
    {
        _buildServer.addListener(this);
    }

    public void buildStarted(SRunningBuild build)
    {
        _log.info("Build Started");

        _server.send();
    }

    public void buildFinished(SRunningBuild build)
    {
        _log.info("Build Finished");

        _server.send();
    }
}