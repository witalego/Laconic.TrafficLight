<%@ include file="/include.jsp" %>
<div>
    <form id="trafficLightSettingsAdminForm">
        <div>
            <span>Protocol: </span>
            <select id="type">
                <option value="Udp" ${type == 'Udp' ? 'selected="selected"' : ''}>Udp</option>
                <option value="Tcp" ${type == 'Tcp' ? 'selected="selected"' : ''}>Tcp</option>
            </select>
        </div>
        <div>
            <input type="submit" value="Save" />
            <!--<forms:submit label="Save"/>-->
            <!--<forms:saving/>-->
        </div>
    </form>

    <bs:linkScript>
        ${teamcityPluginResourcesPath}js/trafficLightSettingsAdmin.js
    </bs:linkScript>
</div>