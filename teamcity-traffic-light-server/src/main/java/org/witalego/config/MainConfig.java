package org.witalego.config;

import org.jdom.Element;
import org.witalego.config.models.MainConfigModel;
import org.witalego.contracts.configs.IMainConfig;
import org.witalego.contracts.logging.ILog;
import org.witalego.track.Trackable;

public class MainConfig extends BaseConfig implements IMainConfig
{
    private final static String EnabledAttributeName = "enabled";
    private final static String ProtocolTypeAttributeName = "protocolType";

    private final Trackable<Boolean> _enabled = new Trackable<>(true);
    private final Trackable<ProtocolType> _protocolType = new Trackable<>(ProtocolType.Udp);

    private ILog _log;

    public MainConfig(ILog log)
    {
        init(_enabled, _protocolType);

        _log = log;
    }

    @Override
    public Boolean getEnabled()
    {
        return _enabled.get();
    }

    @Override
    public void setEnabled(Boolean enabled)
    {
        _log.info("MainConfig.setEnabled: old - %s and new - %s", getEnabled(), enabled);

        _enabled.set(enabled);
    }

    @Override
    public ProtocolType getProtocolType()
    {
        return _protocolType.get();
    }

    @Override
    public void setProtocolType(ProtocolType protocolType)
    {
        _protocolType.set(protocolType);
    }

    @Override
    public MainConfigModel getModel()
    {
        return new MainConfigModel()
        {{
            enabled = _enabled.getModel();
            protocolType = _protocolType.getModel();
        }};
    }

    @Override
    public void load(Element element)
    {
        _log.info("MainConfig: loading config");

        if (element == null)
        {
            _log.info("MainConfig: cannot load config, element is null");
            return;
        }

        getBooleanAttribute(
            element,
            EnabledAttributeName,
            this::setEnabled);

        getAttribute(
            element,
            ProtocolTypeAttributeName,
            this::setProtocolType,

            ProtocolType::valueOf);
    }

    @Override
    public void save(Element element)
    {
        element.setAttribute(EnabledAttributeName, _enabled.toString());
        element.setAttribute(ProtocolTypeAttributeName, _protocolType.toString());
    }
}