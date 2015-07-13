package laconic.teamCity;

import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SRunningBuild;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrafficLightListener extends BuildServerAdapter {
    private static final int RedLightCode = 0x04;
    private static final int AmberLightCode = 0x02;
    private static final int GreenLightCode = 0x01;
    private static final int UdpPort = 2806;
    private InetAddress IpAddress;

    private final SBuildServer buildServer;
    private final Timer timer = new Timer();
    private DatagramSocket socket;

    public static final int TimerDelay = 5000;
    public static final int TimerPeriod = 15000;

    public TrafficLightListener(SBuildServer sBuildServer) {
        buildServer = sBuildServer;

        setInetAddress("255.255.255.255");

        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInetAddress(String ipAddress) {
        try {
            IpAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void register() {
        buildServer.addListener(this);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateBroadcast();
            }
        }, TimerDelay, TimerPeriod);
    }

    public void buildStarted(SRunningBuild build) {
        updateBroadcast();
    }

    public void buildFinished(SRunningBuild build) {
        updateBroadcast();
    }

    private void updateBroadcast() {
        Status status = getWorstStatus();
        int code = status.isSuccessful() ? GreenLightCode : RedLightCode;
        int runningCode = buildServer.getNumberOfRunningBuilds() > 0 ? AmberLightCode : 0;
        sendCode(code | runningCode);
    }

    private Status getWorstStatus() {
        List<SBuildType> allBuildTypes = buildServer.getProjectManager().getAllBuildTypes();
        return buildServer.getStatusProvider().getWorstBuildTypesStatus(allBuildTypes);
    }

    private void sendCode(int code) {
        try {
            byte[] sendData = new byte[]{0x28, 0x06, (byte)code};
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, UdpPort);
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
