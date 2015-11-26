package com.localhost.teamcity.trafficLightPlugin.admin;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

public class TrafficLightAdminPage extends AdminPage
{
    public TrafficLightAdminPage(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor descriptor)
    {
        super(pagePlaces);

        setPluginName("trafficLightPlugin");
        setIncludeUrl(descriptor.getPluginResourcesPath("/admin/Hello.jsp"));
        setTabTitle("Traffic Light");
        setPosition(PositionConstraint.after("plugins"));

        register();
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request)
    {
        return super.isAvailable(request) && checkHasGlobalPermission(request, Permission.CHANGE_SERVER_SETTINGS);
    }

    @NotNull
    public String getGroup()
    {
        return SERVER_RELATED_GROUP;
    }
}