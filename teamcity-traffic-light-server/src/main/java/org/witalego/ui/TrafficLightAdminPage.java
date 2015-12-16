package org.witalego.ui;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.witalego.Constants;
import org.witalego.config.ProtocolType;
import org.witalego.contracts.configs.IMainConfig;
import org.witalego.contracts.configs.server.ITcpConfig;
import org.witalego.contracts.configs.server.IUdpConfig;
import org.witalego.contracts.logging.ILog;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficLightAdminPage extends AdminPage
{
    private final static List<String> _protocolTypes = new ArrayList<String>()
    {{
        add(ProtocolType.Udp.toString());
        add(ProtocolType.Tcp.toString());
    }};

    private IMainConfig _mainConfig;
    private IUdpConfig _udpConfig;
    private ITcpConfig _tcpConfig;
    private ILog _log;

    private final String _jspHome;
    private final String _version;

    public TrafficLightAdminPage(
        PagePlaces pagePlaces,
        PluginDescriptor descriptor,
        IMainConfig mainConfig,
        IUdpConfig udpConfig,
        ITcpConfig tcpConfig,
        ILog log)
    {
        super(pagePlaces);

        _mainConfig = mainConfig;
        _udpConfig = udpConfig;
        _tcpConfig = tcpConfig;
        _log = log;

        _log.info("TrafficLightAdminPage");

        _jspHome = descriptor.getPluginResourcesPath();
        _version = descriptor.getPluginVersion();

        setPluginName(Constants.PluginName);
        setIncludeUrl(descriptor.getPluginResourcesPath(Constants.AdminPage));
        setTabTitle(Constants.PluginTabTitle);
        setPosition(PositionConstraint.after("plugins"));

        register();
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request)
    {
        return super.isAvailable(request) && checkHasGlobalPermission(request, Permission.CHANGE_SERVER_SETTINGS);
    }

    @NotNull
    @Override
    public String getGroup()
    {
        return SERVER_RELATED_GROUP;
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request)
    {
        super.fillModel(model, request);

        _log.info("TrafficLightAdminPage.fillModel");

        model.put("jspHome", _jspHome);
        model.put("pluginVersion", _version);
        model.put("actionUrl", Constants.AdminController);

        fillModel(model, _mainConfig);
        fillModel(model, _udpConfig);
        fillModel(model, _tcpConfig);
    }

    private void fillModel(Map<String, Object> model, IMainConfig config)
    {
        _log.info("TrafficLightAdminPage.fillModel: main config");

        model.put("protocolType", config.getProtocolType());
        model.put("protocolTypes", _protocolTypes);
        model.put("disabled", !config.getEnabled());
    }

    private void fillModel(Map<String, Object> model, IUdpConfig config)
    {
        _log.info("TrafficLightAdminPage.fillModel: udp config");

        model.put(
            "udp",
            new HashMap<String, Object>()
            {{
                put("port", config.getPort());
                put("ipAddress", config.getIpAddress());
                put("period", config.getPeriod());
            }});
    }

    private void fillModel(Map<String, Object> model, ITcpConfig config)
    {
        _log.info("TrafficLightAdminPage.fillModel: tcp config");

        model.put(
            "tcp",
            new HashMap<String, Object>()
            {{
                put("port", config.getPort());
            }});
    }
}