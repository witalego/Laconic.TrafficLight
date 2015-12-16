package org.witalego.contracts.listeners.configs;

import org.witalego.config.models.MainConfigModel;

public interface IMainConfigListener extends IConfigListener
{
    void changed(MainConfigModel model);
}