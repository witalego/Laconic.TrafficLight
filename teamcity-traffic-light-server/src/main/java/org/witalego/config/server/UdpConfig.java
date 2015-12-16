package org.witalego.config.server;

import org.jdom.Element;
import org.witalego.config.BaseConfig;
import org.witalego.config.models.UdpConfigModel;
import org.witalego.contracts.configs.server.IUdpConfig;
import org.witalego.contracts.logging.ILog;
import org.witalego.track.Trackable;

public class UdpConfig extends BaseConfig implements IUdpConfig
{
    private final static String UdpElementName = "udp";
    private final static String PortElementName = "port";
    private final static String IpAddressElementName = "ip-address";
    private final static String PeriodElementName = "period";

    private Trackable<Integer> _port = new Trackable<>(2806);
    private Trackable<String> _ipAddress = new Trackable<>("255.255.255.255");
    private Trackable<Integer> _period = new Trackable<>(15);
    private ILog _log;

    public UdpConfig(ILog log)
    {
        init(_port, _ipAddress, _period);

        _log = log;
    }

    @Override
    public Integer getPort()
    {
        return _port.get();
    }

    @Override
    public void setPort(Integer port)
    {
        _log.info("UdpConfig.setPort: old - %s and new - %s", getPort(), port);

        _port.set(port);
    }

    @Override
    public String getIpAddress()
    {
        return _ipAddress.get();
    }

    @Override
    public void setIpAddress(String ipAddress)
    {
        _ipAddress.set(ipAddress);
    }

    @Override
    public Integer getPeriod()
    {
        return _period.get();
    }

    @Override
    public void setPeriod(Integer period)
    {
        _period.set(period);
    }

    @Override
    public UdpConfigModel getModel()
    {
        return new UdpConfigModel()
        {{
            port = _port.getModel();
            ipAddress = _ipAddress.getModel();
            period = _period.getModel();
        }};
    }

    @Override
    public void load(Element element)
    {
        if (element == null)
        {
            return;
        }

        Element udpElement = element.getChild(UdpElementName);
        if (udpElement == null)
        {
            return;
        }


        getIntegerElement(
            udpElement,
            PortElementName,
            this::setPort);

        getStringElement(
            udpElement,
            IpAddressElementName,
            this::setIpAddress);

        getIntegerElement(
            udpElement,
            PeriodElementName,
            this::setPeriod);
    }

    @Override
    public void save(Element element)
    {
        Element tcpElement = getElement(element, UdpElementName);

        setElement(tcpElement, PortElementName, _port);
        setElement(tcpElement, IpAddressElementName, _ipAddress);
        setElement(tcpElement, PeriodElementName, _period);
    }
}