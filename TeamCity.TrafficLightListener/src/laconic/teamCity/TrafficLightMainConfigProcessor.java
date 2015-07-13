package laconic.teamCity;

import jetbrains.buildServer.serverSide.MainConfigProcessor;
import org.jdom.Attribute;
import org.jdom.Element;

//  Sample configuration:
//  <server>
//    <LaconicTeamCityTrafficLightListener UpdateIpAddress="192.168.1.100"/>
//  </server>

public class TrafficLightMainConfigProcessor implements MainConfigProcessor {
    private final TrafficLightListener trafficLightListener;

    public TrafficLightMainConfigProcessor(TrafficLightListener trafficLightListener) {
        this.trafficLightListener = trafficLightListener;
    }

    public void readFrom(Element rootElement) {
        Element element = rootElement.getChild("LaconicTeamCityTrafficLightListener");
        if (element == null) {
            return;
        }

        Attribute updateIpAddressAttribute = element.getAttribute("UpdateIpAddress");
        if (updateIpAddressAttribute == null) {
            return;
        }

        trafficLightListener.setInetAddress(updateIpAddressAttribute.getValue());
    }

    public void writeTo(Element parentElement) {
    }
}
