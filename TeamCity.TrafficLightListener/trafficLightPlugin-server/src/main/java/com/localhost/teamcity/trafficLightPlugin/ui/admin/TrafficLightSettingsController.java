package com.localhost.teamcity.trafficLightPlugin.ui.admin;

import com.intellij.openapi.diagnostic.Logger;
import com.localhost.teamcity.trafficLightPlugin.config.ProtocolType;
import com.localhost.teamcity.trafficLightPlugin.config.TrafficLightMainConfig;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class TrafficLightSettingsController extends BaseController
{
    private static Logger Log = Loggers.SERVER;
    private PluginDescriptor _descriptor;
    private TrafficLightMainConfig _trafficLightMainConfig;

    public TrafficLightSettingsController(
        @NotNull WebControllerManager manager,
        @NotNull PluginDescriptor descriptor,
        TrafficLightMainConfig trafficLightMainConfig)
    {
        _descriptor = descriptor;
        _trafficLightMainConfig = trafficLightMainConfig;

        manager.registerController("/trafficLight/adminSettings.html", this);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception
    {
        Log.info("TrafficLightServer [TrafficLightSettingsController] - doHandle");

        ProtocolType type = ProtocolType.valueOf(request.getParameter("type"));

        _trafficLightMainConfig.setType(type);
        _trafficLightMainConfig.save();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("message", "Saved");

        //if(httpServletRequest.getParameter("save") != null)
        //{
        //    Log.info("TrafficLightServer [TrafficLightSettingsController] - doHandle.save");
        //    //params = this.handleConfigurationChange(request);
        //}

        return new ModelAndView(_descriptor.getPluginResourcesPath("/admin/ajaxEdit.jsp"), params);
    }
}