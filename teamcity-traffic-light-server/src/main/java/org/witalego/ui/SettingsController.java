package org.witalego.ui;


import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.witalego.Constants;
import org.witalego.config.ProtocolType;
import org.witalego.contracts.IConfigChangeListener;
import org.witalego.contracts.configs.IMainConfig;
import org.witalego.contracts.configs.server.ITcpConfig;
import org.witalego.contracts.configs.server.IUdpConfig;
import org.witalego.contracts.logging.ILog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


public class SettingsController extends BaseController
{
    private static final String ActionParameter = "action";
    private static final String SaveAction = "save";
    private static final String EnableAction = "enable";

    private final PluginDescriptor _descriptor;
    private final IConfigChangeListener _configChangeListener;
    private final IMainConfig _mainConfig;
    private final IUdpConfig _udpConfig;
    private final ITcpConfig _tcpConfig;
    private final ILog _log;

    public SettingsController(
        WebControllerManager manager,
        PluginDescriptor descriptor,
        IConfigChangeListener configChangeListener,
        IMainConfig mainConfig,
        IUdpConfig udpConfig,
        ITcpConfig tcpConfig,
        ILog log)
    {
        _descriptor = descriptor;
        _configChangeListener = configChangeListener;
        _mainConfig = mainConfig;
        _udpConfig = udpConfig;
        _tcpConfig = tcpConfig;
        _log = log;

        manager.registerController(Constants.AdminController, this);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response)
    {
        _log.info("SettingsController.doHandle");

        _log.info("SettingsController.doHandle parameters");
        request.getParameterMap().forEach((name, values) ->
        {
            _log.info("    Parameter [" + name + "]:[" + String.join(", ", values) + "]");
        });

        String action = request.getParameter(ActionParameter);
        _log.info("SettingsController action = " + action);

        HashMap<String, Object> params = null;

        switch (action)
        {
            case SaveAction:
                params = processSaveAction(request);
                break;

            case EnableAction:
                params = processEnableAction(request);
                break;
        }

        return new ModelAndView(_descriptor.getPluginResourcesPath() + "admin/ajaxEdit.jsp", params);
    }

    private HashMap<String, Object> processSaveAction(HttpServletRequest request)
    {
        _log.info("SettingsController.processSaveAction: begin");

        ProtocolType protocolType = ProtocolType.valueOf(request.getParameter("protocolType"));
        _mainConfig.setProtocolType(protocolType);

        if (protocolType == ProtocolType.Udp)
        {
            _log.info("SettingsController.processSaveAction: udp");

            _udpConfig.setPort(Integer.parseInt(request.getParameter("udp[port]")));
            _udpConfig.setIpAddress(request.getParameter("udp[ipAddress]"));
            _udpConfig.setPeriod(Integer.parseInt(request.getParameter("udp[period]")));
        }
        else if (protocolType == ProtocolType.Tcp)
        {
            _log.info("SettingsController.processSaveAction: tcp");

            _tcpConfig.setPort(Integer.parseInt(request.getParameter("tcp[port]")));
        }


        _configChangeListener.save();

        return new HashMap<String, Object>()
        {{
            put("message", "Saved");
        }};
    }

    private HashMap<String, Object> processEnableAction(HttpServletRequest request)
    {
        _log.info("SettingsController.processEnableAction");

        _mainConfig.setEnabled(Boolean.parseBoolean(request.getParameter("enabled")));

        _configChangeListener.save();

        return new HashMap<>();
    }

    /*
    private boolean isPortInUse(int port)
    {
        _log.info("isPortInUse port = " + port);

        Boolean portInUse = false;

        try
        {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
        }
        catch(IOException e)
        {
            portInUse = true;
        }

        _log.info("isPortInUse " + portInUse);
        return portInUse;
    }
    */
}