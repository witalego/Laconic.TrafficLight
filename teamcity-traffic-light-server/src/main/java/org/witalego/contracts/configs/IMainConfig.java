package org.witalego.contracts.configs;

import org.witalego.config.ProtocolType;
import org.witalego.config.models.MainConfigModel;

public interface IMainConfig extends IXmlConfig
{
    Boolean getEnabled();
    void setEnabled(Boolean enabled);

    ProtocolType getProtocolType();
    void setProtocolType(ProtocolType protocolType);

    MainConfigModel getModel();
}