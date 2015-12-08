package com.localhost.teamcity.trafficLightPlugin.config;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.JDOMUtil;
import jetbrains.buildServer.configuration.ChangeListener;
import jetbrains.buildServer.configuration.FileWatcher;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.util.FileUtil;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TrafficLightMainConfig implements ChangeListener
{
    private ProtocolType _type = ProtocolType.Udp;

    private static Logger Log = Loggers.SERVER;

    private final FileWatcher fileWatcher;
    private final File configFile;

    public TrafficLightMainConfig(ServerPaths serverPaths)
    {
        configFile = new File(serverPaths.getConfigDir(), "traffic-light-config.xml");

        load();

        fileWatcher = new FileWatcher(configFile);
        fileWatcher.setSleepingPeriod(10000L);
        fileWatcher.registerListener(this);
        fileWatcher.start();
    }

    public ProtocolType getType()
    {
        return _type;
    }

    private void load()
    {
        Log.info("Loading configuration file: " + configFile.getAbsolutePath());

        if (!configFile.exists())
        {
            create();
        }

        try
        {
            load(JDOMUtil.loadDocument(configFile).getRootElement());
        }
        catch (Exception e)
        {
            Log.error(e);
        }
    }

    private void load(Element element)
    {
        Log.info("TrafficLightServer [TrafficLightMainSettings] - load");

        Attribute typeAttribute = element.getAttribute("type");
        if (typeAttribute != null)
        {
            Log.info("TrafficLightServer [TrafficLightMainSettings] - load.notNull");

            _type = ProtocolType.valueOf(typeAttribute.getValue());
        }
    }

    private void create()
    {
        Log.info("TrafficLightServer [TrafficLightMainSettings] - create");

        try
        {
            Document doc = new Document();
            doc.setRootElement(new Element("config"));

            save(doc.getRootElement());

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(configFile));
        }
        catch (IOException io)
        {
            Log.error(io);
        }
    }

    public synchronized void save()
    {
        fileWatcher.runActionWithDisabledObserver(
            new Runnable()
            {
                public void run()
                {
                    FileUtil.processXmlFile(
                        configFile,
                        new FileUtil.Processor()
                        {
                            public void process(Element element)
                            {
                                save(element);
                            }
                        });
                }
            });
    }

    public void save(Element element)
    {
        Log.info("TrafficLightServer [TrafficLightMainSettings] - save");

        element.setAttribute("type", _type.toString());
    }

    @Override
    public void changeOccured(String s)
    {
        Log.info("TrafficLightServer [TrafficLightMainSettings] - changeOccured");

        load();
    }
}