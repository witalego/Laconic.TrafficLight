package org.witalego.config.server;

import org.jdom.Element;
import org.witalego.config.BaseConfig;
import org.witalego.config.models.TcpConfigModel;
import org.witalego.contracts.configs.server.ITcpConfig;
import org.witalego.track.Trackable;

public class TcpConfig extends BaseConfig implements ITcpConfig
{
    private final static String TcpElementName = "tcp";
    private final static String PortElementName = "port";

    private Trackable<Integer> _port = new Trackable<>(2806);

    public TcpConfig()
    {
        init(_port);
    }

    @Override
    public Integer getPort()
    {
        return _port.get();
    }

    @Override
    public void setPort(Integer port)
    {
        _port.set(port);
    }

    @Override
    public TcpConfigModel getModel()
    {
        return new TcpConfigModel()
        {{
            port = _port.getModel();
        }};
    }

    @Override
    public void load(Element element)
    {
        if (element == null)
        {
            return;
        }

        Element tcpElement = element.getChild(TcpElementName);
        if (tcpElement == null)
        {
            return;
        }


        getIntegerElement(
            tcpElement,
            PortElementName,
            this::setPort);
    }

    @Override
    public void save(Element element)
    {
        Element tcpElement = getElement(element, TcpElementName);

        setElement(tcpElement, PortElementName, _port);
    }
}