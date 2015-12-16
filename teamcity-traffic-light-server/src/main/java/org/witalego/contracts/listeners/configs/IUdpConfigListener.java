package org.witalego.contracts.listeners.configs;

import org.witalego.config.models.UdpConfigModel;

public interface IUdpConfigListener extends IConfigListener
{
    void changed(UdpConfigModel model);
}