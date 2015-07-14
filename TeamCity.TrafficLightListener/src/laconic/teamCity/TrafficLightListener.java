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
    private static final int UdpPort = 2806;
    private static final int TimerDelay = 5000;
    private static final int TimerPeriod = 15000;

    private InetAddress _ipAddress;

    private final SBuildServer _buildServer;
    private final Timer _timer = new Timer();
    private DatagramSocket _socket;


    public TrafficLightListener(SBuildServer sBuildServer) {
        _buildServer = sBuildServer;

        setInetAddress("255.255.255.255");

        try {
            _socket = new DatagramSocket();
            _socket.setBroadcast(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInetAddress(String ipAddress) {
        try {
            _ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void register() {
        _buildServer.addListener(this);

        _timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        updateBroadcast();
                    }
                },
                TimerDelay,
                TimerPeriod);
    }

    public void buildStarted(SRunningBuild build) {
        updateBroadcast();
    }

    public void buildFinished(SRunningBuild build) {
        updateBroadcast();
    }

    private void updateBroadcast() {
        Status status = getWorstStatus();
        int runningCode = getRunningCode();

        int code = status.isSuccessful()
                ? LightMode.Green.getValue()
                : LightMode.Red.getValue();

        sendCode(code | runningCode);
    }

    private Status getWorstStatus() {
        List<SBuildType> allBuildTypes = _buildServer.getProjectManager().getAllBuildTypes();
        return _buildServer.getStatusProvider().getWorstBuildTypesStatus(allBuildTypes);
    }

    private int getRunningCode() {
        return _buildServer.getNumberOfRunningBuilds() > 0
                ? LightMode.Yellow.getValue()
                : LightMode.None.getValue();
    }

    private void sendCode(int code) {
        try {
            byte[] sendData = new byte[]{0x28, 0x06, (byte)code};
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, _ipAddress, UdpPort);
            _socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}