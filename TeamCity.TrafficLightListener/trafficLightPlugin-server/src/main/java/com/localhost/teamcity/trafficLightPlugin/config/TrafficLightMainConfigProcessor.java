package com.localhost.teamcity.trafficLightPlugin.config;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.MainConfigProcessor;
import jetbrains.buildServer.serverSide.SBuildServer;
import org.jdom.Element;

public class TrafficLightMainConfigProcessor implements MainConfigProcessor
{
    private static final String Name = "traffic-light";

    private static Logger Log = Loggers.SERVER;

    private TrafficLightMainSettings _settings;
    private SBuildServer _server;

    public TrafficLightMainConfigProcessor(SBuildServer server, TrafficLightMainSettings settings)
    {
        Log.info("TrafficLightServer [TrafficLightMainConfigProcessor] - constructor");

        _server = server;
        _settings = settings;
    }

    public TrafficLightMainSettings getSettings()
    {
        Log.info("TrafficLightServer [TrafficLightMainConfigProcessor] - getSettings");

        return _settings;
    }

    public void register()
    {
        Log.info("TrafficLightServer [TrafficLightMainConfigProcessor] - register");
        _server.registerExtension(MainConfigProcessor.class, "trafficLightExtension", this);
    }

    @Override
    public void readFrom(Element element)
    {
        Log.info("TrafficLightServer [TrafficLightMainConfigProcessor] - readFrom");

        Element rootElement = element.getChild(Name);
        if (rootElement == null)
        {
            Log.info("TrafficLightServer [TrafficLightMainConfigProcessor] - readFrom.nullElement");
            return;
        }

        Log.info("TrafficLightServer [TrafficLightMainConfigProcessor] - readFrom.loadSettings");
        _settings.load(rootElement);
    }

    @Override
    public void writeTo(Element element)
    {
        Log.info("TrafficLightServer [TrafficLightMainConfigProcessor] - readFrom.saveSettings");
        _settings.save(element);
    }
}