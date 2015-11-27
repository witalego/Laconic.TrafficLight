package com.localhost.teamcity.trafficLightPlugin.ui.admin;

import com.localhost.teamcity.trafficLightPlugin.config.TrafficLightMainConfigProcessor;
import com.localhost.teamcity.trafficLightPlugin.config.TrafficLightMainSettings;
import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TrafficLightSettingsAdminPage extends AdminPage
{
    private TrafficLightMainSettings _trafficLightMainSettings;

    public TrafficLightSettingsAdminPage(
            @NotNull PagePlaces pagePlaces,
            @NotNull PluginDescriptor descriptor,
            @NotNull TrafficLightMainConfigProcessor mainConfigProcessor)
    {
        super(pagePlaces);

        _trafficLightMainSettings = mainConfigProcessor.getSettings();

        setPluginName("trafficLightPlugin");
        setIncludeUrl(descriptor.getPluginResourcesPath("/admin/TrafficLightSettings.jsp"));
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

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request)
    {
        super.fillModel(model, request);

        model.put("type", _trafficLightMainSettings.type().toString());
    }
}