package laconic.teamCity;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.MainConfigProcessor;
import org.jdom.Attribute;
import org.jdom.Element;

//  Sample configuration:
//  <server>
//    <LaconicTeamCityTrafficLightListener UpdateIpAddress="192.168.1.100"/>
//  </server>

public class TrafficLightMainConfigProcessor implements MainConfigProcessor {
    private static Logger Log = Loggers.SERVER;
    private final TrafficLightListener trafficLightListener;

    public TrafficLightMainConfigProcessor(TrafficLightListener trafficLightListener) {
        this.trafficLightListener = trafficLightListener;
    }

    public void readFrom(Element rootElement) {
        Element element = rootElement.getChild("LaconicTeamCityTrafficLightListener");
        if (element == null) {
            Log.info("[TrafficLightMainConfigProcessor][readFrom] element is null.");
            return;
        }

        Attribute updateIpAddressAttribute = element.getAttribute("UpdateIpAddress");
        if (updateIpAddressAttribute == null) {
            Log.info("[TrafficLightMainConfigProcessor][readFrom] updateIpAddressAttribute is null.");
            return;
        }

        String ip = updateIpAddressAttribute.getValue();
        Log.info("[TrafficLightMainConfigProcessor][readFrom] ip is " + ip);
        trafficLightListener.setInetAddress(ip);
    }

    public void writeTo(Element parentElement) {
    }
}