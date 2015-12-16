package org.witalego.contracts.configs.server;

import org.witalego.config.models.UdpConfigModel;
import org.witalego.contracts.configs.IXmlConfig;

public interface IUdpConfig extends IXmlConfig
{
    Integer getPort();
    void setPort(Integer port);

    String getIpAddress();
    void setIpAddress(String ipAddress);

    Integer getPeriod();
    void setPeriod(Integer period);

    UdpConfigModel getModel();
}