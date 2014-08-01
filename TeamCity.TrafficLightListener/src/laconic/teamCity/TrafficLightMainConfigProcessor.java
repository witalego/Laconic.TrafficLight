/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package laconic.teamCity;

import org.jdom.Attribute;
import org.jdom.Element;

//  Sample configuration:
//  <server>
//    <LaconicTeamCityTrafficLightListener UpdateIpAddress="192.168.1.100"/>
//  </server>

public class TrafficLightMainConfigProcessor implements  jetbrains.buildServer.serverSide.MainConfigProcessor
{
    private final TrafficLightListener trafficLightListener;

    public TrafficLightMainConfigProcessor(TrafficLightListener trafficLightListener) {
        this.trafficLightListener = trafficLightListener;
    }

    public void readFrom(Element rootElement) {
        Element element = rootElement.getChild("LaconicTeamCityTrafficLightListener");
        if (element == null) return;

        Attribute updateIpAddressAttribute = element.getAttribute("UpdateIpAddress");
        if (updateIpAddressAttribute == null) return;

        trafficLightListener.setInetAddress(updateIpAddressAttribute.getValue());
    }

    public void writeTo(Element parentElement) {

    }
}
