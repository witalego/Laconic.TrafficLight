package org.witalego.server;

import org.witalego.config.models.MainConfigModel;
import org.witalego.contracts.IConfigChangeListener;
import org.witalego.contracts.listeners.configs.IMainConfigListener;
import org.witalego.contracts.logging.ILog;
import org.witalego.contracts.server.IServer;
import org.witalego.contracts.server.IServerFactory;

public class Server implements IMainConfigListener
{
    private final BuildServerWrapper _buildServerWrapper;
    private IConfigChangeListener _configChangeListener;
    private final ILog _log;

    private IServer _server = null;
    private IServerFactory _serverFactory;

    public Server(
        IServerFactory serverFactory,
        BuildServerWrapper buildServerWrapper,
        IConfigChangeListener configChangeListener,
        ILog log)
    {
        _serverFactory = serverFactory;
        _buildServerWrapper = buildServerWrapper;
        _configChangeListener = configChangeListener;
        _log = log;

        _configChangeListener.register(this);
    }

    public synchronized void send()
    {
        if (_server == null)
        {
            return;
        }

        _server.send(_buildServerWrapper.getCode());
    }

    @Override
    public void changed(MainConfigModel model)
    {
        _log.info("Server.MainConfigChanged");

        if (model.enabled.isChanged())
        {
            if (_server == null)
            {
                _server = _serverFactory.getServer(model.protocolType.newValue);
            }

            _log.info("Server.MainConfigChanged: enabled was changed from " + model.enabled.oldValue + " to " + model.enabled.newValue);
            if (model.enabled.newValue)
            {
                _log.info("Server.MainConfigChanged: start server " + _server.getClass().getName());
                _server.startServer();
            }
            else
            {
                _log.info("Server.MainConfigChanged: stop server " + _server.getClass().getName());
                _server.stopServer();
            }
        }
        else if (model.protocolType.isChanged())
        {
            _log.info("Server.MainConfigChanged: protocol was changed from " + model.protocolType.oldValue + " to " + model.protocolType.newValue);
            _server.stopServer();

            _server = _serverFactory.getServer(model.protocolType.newValue);

            _server.startServer();
        }
        else
        {
            _log.info("Server.MainConfigChanged: nothing was changed");
            if (model.enabled.newValue)
            {
                _server = _serverFactory.getServer(model.protocolType.newValue);
                _log.info("Server.MainConfigChanged: server selectrd " + _server.getClass().getName());
            }
            else
            {
                _server = null;
                _log.info("Server.MainConfigChanged: do nothing");
            }
        }
    }
}