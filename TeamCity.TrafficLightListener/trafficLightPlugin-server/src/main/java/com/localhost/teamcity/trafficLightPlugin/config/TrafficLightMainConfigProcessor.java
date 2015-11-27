package com.localhost.teamcity.trafficLightPlugin.config;

import jetbrains.buildServer.serverSide.MainConfigProcessor;
import org.jdom.Element;

public class TrafficLightMainConfigProcessor implements MainConfigProcessor
{
    private static final String Name = "traffic-light";

    private TrafficLightMainSettings _settings;

    public TrafficLightMainConfigProcessor(TrafficLightMainSettings settings)
    {
        _settings = settings;
    }

    public TrafficLightMainSettings getSettings()
    {
        return _settings;
    }

    @Override
    public void readFrom(Element element)
    {
        Element rootElement = element.getChild(Name);
        if (rootElement == null)
        {
            rootElement = element.addContent(new Element(Name));
        }

        _settings.load(rootElement);

        writeTo(rootElement);
    }

    @Override
    public void writeTo(Element element)
    {
        _settings.save(element);
    }
}