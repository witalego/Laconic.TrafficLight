package org.witalego.server;

import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SBuildType;

import java.util.List;

public class BuildServerWrapper
{
    private final SBuildServer _buildServer;

    public BuildServerWrapper(SBuildServer buildServer)
    {
        _buildServer = buildServer;
    }

    public byte getCode()
    {
        Status status = getWorstStatus();
        LightMode runningCode = getRunningCode();

        LightMode code = status.isSuccessful()
            ? LightMode.Green
            : LightMode.Red;

        return (byte)(code.getValue() | runningCode.getValue());
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