package com.localhost.teamcity.trafficLightPlugin.config;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;
import org.jdom.Attribute;
import org.jdom.Element;

public class TrafficLightMainSettings
{
    private ProtocolType _type = ProtocolType.Udp;

    private static Logger Log = Loggers.SERVER;

    public ProtocolType type()
    {
        return _type;
    }

    public void load(Element element)
    {
        Log.info("TrafficLightServer [TrafficLightMainSettings] - load");

        Attribute typeAttribute = element.getAttribute("type");
        if (typeAttribute != null)
        {
            Log.info("TrafficLightServer [TrafficLightMainSettings] - load.notNull");
            _type = ProtocolType.valueOf(typeAttribute.getValue());
        }
    }

    public void save(Element element)
    {
        Log.info("TrafficLightServer [TrafficLightMainSettings] - save");
        element.setAttribute("type", _type.toString());
    }
}