package com.localhost.teamcity.trafficLightPlugin;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppServer extends BaseController
{
    private PluginDescriptor _descriptor;

    public AppServer (WebControllerManager manager, PluginDescriptor descriptor)
    {
        manager.registerController("/demoPlugin.html",this);

        _descriptor = descriptor;
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) throws Exception
    {
        return new ModelAndView(_descriptor.getPluginResourcesPath("Hello.jsp"));
    }
}