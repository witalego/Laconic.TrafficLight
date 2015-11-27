package com.localhost.teamcity.trafficLightPlugin.ui.admin;

import com.intellij.openapi.diagnostic.Logger;
import com.localhost.teamcity.trafficLightPlugin.config.TrafficLightMainSettings;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrafficLightSettingsController extends BaseController
{
    private static Logger Log = Loggers.SERVER;
    private PluginDescriptor _descriptor;

    public TrafficLightSettingsController(
            @NotNull WebControllerManager manager,
            @NotNull PluginDescriptor descriptor,
            @NotNull TrafficLightMainSettings settings)
    {
        _descriptor = descriptor;
        manager.registerController("/trafficLight/adminSettings.html", this);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) throws Exception
    {
        Log.info("TrafficLightServer [TrafficLightSettingsController] - doHandle");

        //if(httpServletRequest.getParameter("save") != null)
        //{
        //    Log.info("TrafficLightServer [TrafficLightSettingsController] - doHandle.save");
        //    //params = this.handleConfigurationChange(request);
        //}

        return new ModelAndView(_descriptor.getPluginResourcesPath("/admin/TrafficLightSettings.jsp"));
    }
}