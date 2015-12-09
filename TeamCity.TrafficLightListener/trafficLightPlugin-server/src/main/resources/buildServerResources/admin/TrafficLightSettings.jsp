<%@ include file="/include.jsp" %>
<div>
    <form id="trafficLightSettingsAdminForm" action="/yammerNotifier/adminSettings.html" method="post" onsubmit="return TrafficLightSettingsAdmin.save()">
        <div>
            <span>Protocol: </span>
            <select name="type">
                <option value="Udp" ${type == 'Udp' ? 'selected="selected"' : ''}>Udp</option>
                <option value="Tcp" ${type == 'Tcp' ? 'selected="selected"' : ''}>Tcp</option>
            </select>
        </div>
        <div>
            <input type="submit" value="Save">
        </div>
    </form>

    <bs:linkScript>
        ${jspHome}js/trafficLightSettingsAdmin.js
    </bs:linkScript>
</div>