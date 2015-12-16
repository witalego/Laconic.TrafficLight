package org.witalego.contracts.configs.server;

import org.witalego.config.models.TcpConfigModel;
import org.witalego.contracts.configs.IXmlConfig;

public interface ITcpConfig extends IXmlConfig
{
    Integer getPort();
    void setPort(Integer port);

    TcpConfigModel getModel();
}