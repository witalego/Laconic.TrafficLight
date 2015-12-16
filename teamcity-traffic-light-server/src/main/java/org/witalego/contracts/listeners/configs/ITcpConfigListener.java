package org.witalego.contracts.listeners.configs;

import org.witalego.config.models.TcpConfigModel;

public interface ITcpConfigListener extends IConfigListener
{
    void changed(TcpConfigModel model);
}