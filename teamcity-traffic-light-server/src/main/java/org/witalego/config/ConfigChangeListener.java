package org.witalego.config;

import com.intellij.openapi.util.JDOMUtil;
import jetbrains.buildServer.configuration.ChangeListener;
import jetbrains.buildServer.configuration.FileWatcher;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.util.FileUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.witalego.Constants;
import org.witalego.contracts.IConfigChangeListener;
import org.witalego.contracts.configs.IMainConfig;
import org.witalego.contracts.configs.server.ITcpConfig;
import org.witalego.contracts.configs.server.IUdpConfig;
import org.witalego.contracts.listeners.configs.IConfigListener;
import org.witalego.contracts.listeners.configs.IMainConfigListener;
import org.witalego.contracts.listeners.configs.ITcpConfigListener;
import org.witalego.contracts.listeners.configs.IUdpConfigListener;
import org.witalego.contracts.logging.ILog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ConfigChangeListener implements ChangeListener, IConfigChangeListener
{
    private final static String ElementRootName = "config";

    private final IMainConfig _mainConfig;
    private final IUdpConfig _udpConfig;
    private final ITcpConfig _tcpConfig;
    private final ILog _log;

    private final File _configFile;
    private final FileWatcher _fileWatcher;
    private final Set<IMainConfigListener> _mainConfigListeners = new HashSet<>();
    private final Set<IUdpConfigListener> _udpConfigListeners = new HashSet<>();
    private final Set<ITcpConfigListener> _tcpConfigListeners = new HashSet<>();

    public ConfigChangeListener(
        ServerPaths serverPaths,
        IMainConfig mainConfig,
        IUdpConfig udpConfig,
        ITcpConfig tcpConfig,
        ILog log)
    {
        _mainConfig = mainConfig;
        _udpConfig = udpConfig;
        _tcpConfig = tcpConfig;
        _log = log;

        _configFile = new File(serverPaths.getConfigDir(), Constants.ConfigFileName);

        reload();

        _fileWatcher = new FileWatcher(_configFile);
        _fileWatcher.setSleepingPeriod(10000L);
        _fileWatcher.registerListener(this);
        _fileWatcher.start();
    }

    public synchronized void save()
    {
        _log.info("Saving configuration file: " + _configFile.getAbsolutePath());

        _fileWatcher.runActionWithDisabledObserver(
            () -> FileUtil.processXmlFile(_configFile, this::save));
    }

    @Override
    public void changeOccured(String s)
    {
        reload();
    }

    private void reload()
    {
        _log.info("ConfigChangeListener: loading configuration file: " + _configFile.getAbsolutePath());

        if (!_configFile.exists())
        {
            _log.info("ConfigChangeListener: configuration file not exists");

            create();
        }

        Document document = load();
        if (document != null)
        {
            _mainConfig.load(document.getRootElement());
            _udpConfig.load(document.getRootElement());
            _tcpConfig.load(document.getRootElement());

            fireMainConfigChanged(_mainConfig);
            fireUdpConfigChanged(_udpConfig);
            fireTcpConfigChanged(_tcpConfig);
        }
    }

    private void create()
    {
        _log.info("ConfigChangeListener: creating configuration file");

        try
        {
            Document doc = new Document();
            doc.setRootElement(new Element(ElementRootName));

            save(doc.getRootElement());

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(_configFile));
        }
        catch (IOException e)
        {
            _log.error("Failed to create configuration file: " + _configFile.getAbsolutePath(), e);
        }
    }

    private Document load()
    {
        try
        {
            if (_configFile.isFile())
            {
                return JDOMUtil.loadDocument(_configFile);
            }
        }
        catch (JDOMException e)
        {
            _log.error("Failed to load xml configuration file: " + _configFile.getAbsolutePath(), e);
        }
        catch (IOException e)
        {
            _log.error("I/O error occurred on attempt to load xml configuration file: " + _configFile.getAbsolutePath(), e);
        }

        return null;
    }

    private void save(Element element)
    {
        _mainConfig.save(element);
        _udpConfig.save(element);
        _tcpConfig.save(element);

        fireMainConfigChanged(_mainConfig);
        fireUdpConfigChanged(_udpConfig);
        fireTcpConfigChanged(_tcpConfig);
    }

    @Override
    public void register(IConfigListener listener)
    {
        _log.info("ConfigChangeListener.register: new listener was added " + listener.getClass().getName());

        if (IMainConfigListener.class.isInstance(listener))
        {
            _log.info("ConfigChangeListener.register: main config listener");
            IMainConfigListener mainConfigListener = (IMainConfigListener)listener;
            _mainConfigListeners.add(mainConfigListener);
            mainConfigListener.changed(_mainConfig.getModel());
        }
        else if (IUdpConfigListener.class.isInstance(listener))
        {
            _log.info("ConfigChangeListener.register: udp config listener");
            IUdpConfigListener udpConfigListener = (IUdpConfigListener)listener;
            _udpConfigListeners.add(udpConfigListener);
            udpConfigListener.changed(_udpConfig.getModel());
        }
        else if (ITcpConfigListener.class.isInstance(listener))
        {
            _log.info("ConfigChangeListener.register: tcp config listener");
            ITcpConfigListener tcpConfigListener = (ITcpConfigListener)listener;
            _tcpConfigListeners.add(tcpConfigListener);
            tcpConfigListener.changed(_tcpConfig.getModel());
        }
        else
        {
            _log.info("ConfigChangeListener.register: unknown listener");
        }
    }

    private void fireMainConfigChanged(IMainConfig config)
    {
        if (!config.isChanged())
        {
            _log.info("ConfigChangeListener.fireMainConfigChanged not changed.");
            return;
        }

        _log.info("ConfigChangeListener.fireMainConfigChanged changed, iterating");
        _mainConfigListeners.forEach(listener -> listener.changed(config.getModel()));

        config.resetIsChanged();
    }

    private void fireUdpConfigChanged(IUdpConfig config)
    {
        if (!config.isChanged())
        {
            _log.info("ConfigChangeListener.fireUdpConfigChanged not changed.");
            return;
        }

        _log.info("ConfigChangeListener.fireUdpConfigChanged changed, iterating");
        _udpConfigListeners.forEach(listener -> listener.changed(config.getModel()));

        config.resetIsChanged();
    }

    private void fireTcpConfigChanged(ITcpConfig config)
    {
        if (!config.isChanged())
        {
            _log.info("ConfigChangeListener.fireTcpConfigChanged not changed.");
            return;
        }

        _log.info("ConfigChangeListener.fireTcpConfigChanged changed, iterating");
        _tcpConfigListeners.forEach(listener -> listener.changed(config.getModel()));

        config.resetIsChanged();
    }
}