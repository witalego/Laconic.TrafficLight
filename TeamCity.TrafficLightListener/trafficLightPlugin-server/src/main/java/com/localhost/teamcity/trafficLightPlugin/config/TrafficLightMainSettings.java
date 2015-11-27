package com.localhost.teamcity.trafficLightPlugin.config;

import org.jdom.Attribute;
import org.jdom.Element;

public class TrafficLightMainSettings
{
    private ProtocolType _type = ProtocolType.Udp;

    public ProtocolType type()
    {
        return _type;
    }

    public void load(Element element)
    {
        Attribute typeAttribute = element.getAttribute("type");
        if (typeAttribute != null)
        {
            _type = ProtocolType.valueOf(typeAttribute.getValue());
        }
    }

    public void save(Element element)
    {
        element.setAttribute("type", _type.toString());
    }
}