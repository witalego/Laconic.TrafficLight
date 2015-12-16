<%@ include file="/include.jsp" %>

<bs:linkCSS dynamic="${true}">
    ${jspHome}css/admin-styles.css
</bs:linkCSS>

<script type="text/javascript">
    var url = "${actionUrl}";
</script>

<div id="settingsContainer">
    <form id="trafficLightAdminForm">
        <div class="editNotificatorSettingsPage">
            <div>
                <span class="trafficLightVersionInfo">Version: <c:out value='${pluginVersion}'/>&nbsp;
                    <a href="https://github.com/witalego/teamcity-traffic-light" class="helpIcon"
                        style="vertical-align: middle;" target="_blank"><bs:helpIcon/></a>
                </span>
            </div>
            <c:choose>
                <c:when test="${disabled}">
                    <div class="pauseNote" style="margin-bottom: 1em;">
                        The plugin is <strong>disabled</strong>. All build notifications are suspended&nbsp;&nbsp;
                        <a class="btn btn_mini" href="#" id="enable-btn">Enable</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="margin-left: 0.6em;">
                        The Plugin is <strong>enabled</strong>&nbsp;&nbsp;<a class="btn btn_mini" href="#" id="disable-btn">Disable</a>
                    </div>
                </c:otherwise>
            </c:choose>

            <bs:messages key="message"/>
            <br/>

            <div id="errors" class="config-errors"></div>

            <table class="runnerFormTable">
                <tr class="groupingTitle">
                    <td colspan="2">General Configuration&nbsp;
                        <a href="https://github.com/witalego/teamcity-traffic-light"
                            class="helpIcon" style="vertical-align: middle;"
                            target="_blank"><bs:helpIcon/></a>
                    </td>
                </tr>


                <tr>
                    <th>
                        <label for="protocolType">Protocol: </label>
                    </th>
                    <td>
                        <select name="protocolType" style="width: 300px;" ${disabled ? "disabled='disabled'" : ''}>
                            <c:forEach var="type" items="${protocolTypes}">
                                <option value="${type}" ${type == protocolType ? 'selected="selected"' : ''}>${type}</option>
                            </c:forEach>
                        </select>
                        <span class="smallNote">Network protocol type</span>
                    </td>
                </tr>


                <tr class="groupingTitle udp" style='display:${protocolType == "Udp" ? "" : "none"};'>
                    <td colspan="2"><span id="serverConfigurationTitle">Udp</span> Server Configuration</td>
                </tr>

                <tr class="udp" style='display:${protocolType == "Udp" ? "" : "none"};'>
                    <th>
                        <label for="udp-port">Port: <l:star/></label>
                    </th>
                    <td>
                        <input name="udp-port" value="${udp.port}" type="number" min="0" max="65535" required ${disabled ? "disabled='disabled'" : ''} style="width: 300px;" />
                        <span class="smallNote">Network port</span>
                    </td>
                </tr>

                <tr class="udp" style='display:${protocolType == "Udp" ? "" : "none"};'>
                    <th>
                        <label for="udp-ipAddress">IP Address: <l:star/></label>
                    </th>
                    <td>
                        <input name="udp-ipAddress" value="${udp.ipAddress}" type="text" pattern="\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}" required ${disabled ? "disabled='disabled'" : ''} style="width: 300px;" />
                        <span class="smallNote">IP address in format xxx.xxx.xxx.xxx</span>
                    </td>
                </tr>

                <tr class="udp" style='display:${protocolType == "Udp" ? "" : "none"};'>
                    <th>
                        <label for="udp-period">Period: <l:star/></label>
                    </th>
                    <td>
                        <input name="udp-period" value="${udp.period}" type="number" min="1" max="60" required ${disabled ? "disabled='disabled'" : ''} style="width: 300px;" />
                        <span class="smallNote">Udp package sending frequency in seconds</span>
                    </td>
                </tr>


                <tr class="groupingTitle tcp" style='display:${protocolType == "Tcp" ? "" : "none"};'>
                    <td colspan="2"><span id="serverConfigurationTitle">Tcp</span> Server Configuration</td>
                </tr>

                <tr class="tcp" style='display:${protocolType == "Tcp" ? "" : "none"};'>
                    <th>
                        <label for="tcp-port">Port: <l:star/></label>
                    </th>
                    <td>
                        <input name="tcp-port" value="${tcp.port}" type="number" min="0" max="65535" required ${disabled ? "disabled='disabled'" : ''} style="width: 300px;" />
                        <span class="smallNote">Network port</span>
                    </td>
                </tr>
            </table>

            <div class="saveButtonsBlock">
                <forms:submit label="Save"/>
                <forms:saving/>
            </div>
        </div>
    </form>

    <bs:linkScript>
        ${jspHome}js/trafficLightAdmin.js
    </bs:linkScript>
</div>