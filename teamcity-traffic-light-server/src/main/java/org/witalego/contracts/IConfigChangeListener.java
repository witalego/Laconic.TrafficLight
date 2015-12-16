package org.witalego.contracts;

import org.witalego.contracts.listeners.configs.IConfigListener;

public interface IConfigChangeListener
{
    void save();
    void register(IConfigListener listener);
}