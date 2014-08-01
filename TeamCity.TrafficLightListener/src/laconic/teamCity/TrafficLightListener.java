package laconic.teamCity;

import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.BuildType;
import jetbrains.buildServer.messages.BuildMessage1;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.serverSide.problems.BuildProblemInfo;
import jetbrains.buildServer.tests.TestName;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.vcs.SVcsRoot;
import jetbrains.buildServer.vcs.VcsModification;
import jetbrains.buildServer.vcs.VcsRoot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.security.x509.IPAddressName;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class TrafficLightListener implements BuildServerListener {
    public static final int TimerDelay = 5000;
    public static final int TimerPeriod = 15000;
    private static final int RedLightCode = 0x04;
    private static final int AmberLightCode = 0x02;
    private static final int GreenLightCode = 0x01;
    private static final int UdpPort = 2806;
    private InetAddress IpAddress;

    private final SBuildServer buildServer;
    private final Timer timer = new Timer();
    private DatagramSocket socket;

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

    private void updateBroadcast(){
        Status status = getWorstStatus();
        int code = status.isSuccessful() ? GreenLightCode : RedLightCode;
        int runningCode = buildServer.getNumberOfRunningBuilds() > 0 ? AmberLightCode : 0;
        sendCode(code | runningCode);
    }

    private Status getWorstStatus(){
        List<SBuildType> allBuildTypes = buildServer.getProjectManager().getAllBuildTypes();
        return buildServer.getStatusProvider().getWorstBuildTypesStatus(allBuildTypes);
    }

    private void sendCode(int code)
    {
        try {
            byte[] sendData = new byte[]{0x28, 0x06, (byte)code};
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, UdpPort);
            socket.send(sendPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildChangedStatus(SRunningBuild build, Status oldStatus, Status newStatus) {
    }

    public void agentRegistered(SBuildAgent agent, long currentlyRunningBuildId) {
    }

    public void agentDescriptionUpdated(@NotNull SBuildAgent agent) {
    }

    public void beforeAgentUnregistered(SBuildAgent agent) {
    }

    public void agentUnregistered(SBuildAgent agent) {
    }

    public void agentStatusChanged(@NotNull SBuildAgent agent, boolean wasEnabled, boolean wasAuthorized) {
    }

    public void agentRemoved(SBuildAgent agent) {
    }

    public void buildTypeAddedToQueue(SBuildType buildType) {
    }

    public void buildTypeAddedToQueue(@NotNull SQueuedBuild queuedBuild) {
    }

    public void buildRemovedFromQueue(@NotNull SQueuedBuild queuedBuild, User user, String comment) {
    }

    public void buildPinned(@NotNull SBuild build, @Nullable User user, @Nullable String comment) {
    }

    public void buildUnpinned(@NotNull SBuild build, @Nullable User user, @Nullable String comment) {
    }

    public void buildQueueOrderChanged() {
    }

    public void buildTypeRegistered(SBuildType buildType) {
    }

    public void buildTypeUnregistered(SBuildType buildType) {
    }

    public void buildTypeMoved(SBuildType buildType, SProject original) {
    }

    public void buildTypeTemplateExternalIdChanged(@NotNull BuildTypeTemplate buildTypeTemplate, @NotNull String oldExternalId, @NotNull String newExternalId) {
    }

    public void buildTypeExternalIdChanged(@NotNull SBuildType buildType, @NotNull String oldExternalId, @NotNull String newExternalId) {
    }

    public void projectMoved(@NotNull SProject project, @NotNull SProject originalParentProject) {
    }

    public void beforeBuildTypeDeleted(@NotNull String buildTypeId) {
    }

    public void buildTypeDeleted(@NotNull String buildTypeId) {
    }

    public void buildTypeTemplateDeleted(@NotNull String buildTypeTemplateId) {
    }

    public void buildTypeActiveStatusChanged(SBuildType buildType) {
    }

    public void changesLoaded(SRunningBuild build) {
    }

    public void beforeBuildFinish(SRunningBuild runningBuild) {
    }

    public void buildInterrupted(SRunningBuild build) {
    }

    public void messageReceived(SRunningBuild build, BuildMessage1 message) {
    }

    public void responsibleChanged(@NotNull SBuildType bt, @NotNull ResponsibilityEntry oldValue, @NotNull ResponsibilityEntry newValue) {
    }

    public void responsibleChanged(@NotNull SProject project, @Nullable TestNameResponsibilityEntry oldValue, @NotNull TestNameResponsibilityEntry newValue, boolean isUserAction) {
    }

    public void responsibleChanged(@NotNull SProject project, @NotNull Collection<TestName> testNames, @NotNull ResponsibilityEntry entry, boolean isUserAction) {
    }

    public void responsibleChanged(@NotNull SProject project, @NotNull Collection<BuildProblemInfo> buildProblems, @Nullable ResponsibilityEntry entry) {
    }

    public void responsibleRemoved(SProject project, TestNameResponsibilityEntry entry) {
    }

    public void beforeEntryDelete(SFinishedBuild entry) {
    }

    public void entryDeleted(SFinishedBuild entry) {
    }

    public void projectCreated(String projectId, @Nullable SUser user) {
    }

    public void projectExternalIdChanged(@NotNull SProject project, @NotNull String oldExternalId, @NotNull String newExternalId) {
    }

    public void projectRemoved(String projectId) {
    }

    public void projectPersisted(String projectId) {
    }

    public void projectRestored(String projectId) {
    }

    public void projectArchived(String projectId) {
    }

    public void projectDearchived(String projectId) {
    }

    public void buildTypeTemplatePersisted(@NotNull BuildTypeTemplate buildTemplate) {
    }

    public void buildTypePersisted(@NotNull SBuildType buildType) {
    }

    public void pluginsLoaded() {
    }

    public void changeAdded(@NotNull VcsModification modification, @NotNull VcsRoot root, @Nullable Collection<SBuildType> buildTypes) {
    }

    public void cleanupStarted() {
    }

    public void cleanupFinished() {
    }

    public void serverShutdownComplete() {
    }

    public void sourcesVersionReleased(@NotNull BuildAgent agent) {
    }

    public void sourcesVersionReleased(@NotNull BuildType configuration) {
    }

    public void sourcesVersionReleased(@NotNull BuildType configuration, @NotNull BuildAgent agent) {
    }

    public void labelingFailed(SBuild build, VcsRoot root, Throwable exception) {
    }

    public void labelingSucceed(SBuild build, BuildRevision revision) {
    }

    public void buildTagsChanged(SBuild build, List<String> oldTags, List<String> newTags) {
    }

    public void buildTagsChanged(SBuild build, User user, List<String> oldTags, List<String> newTags) {
    }

    public void buildCommented(@NotNull SBuild build, @Nullable User user, @Nullable String comment) {
    }

    public void serverConfigurationReloaded() {
    }

    public void vcsRootRemoved(@NotNull SVcsRoot root) {
    }

    public void vcsRootUpdated(@NotNull SVcsRoot oldVcsRoot, @NotNull SVcsRoot newVcsRoot) {
    }

    public void vcsRootExternalIdChanged(@NotNull SVcsRoot vcsRoot, @NotNull String oldExternalId, @NotNull String newExternalId) {
    }

    public void vcsRootPersisted(@NotNull SVcsRoot vcsRoot) {
    }

    public void vcsRootsPersisted() {
    }

    public void testsMuted(@NotNull MuteInfo muteInfo) {
    }

    public void testsUnmuted(@Nullable SUser user, @NotNull Map<MuteInfo, Collection<STest>> unmutedGroups) {
    }

    public void buildProblemsMuted(@NotNull MuteInfo muteInfo) {
    }

    public void buildProblemsUnmuted(@Nullable SUser user, @NotNull Map<MuteInfo, Collection<BuildProblemInfo>> unmutedGroups) {
    }

    public void buildProblemsChanged(@NotNull SBuild build, @NotNull List<BuildProblemData> before, @NotNull List<BuildProblemData> after) {
    }

    public void statisticValuePublished(@NotNull SBuild build, @NotNull String valueTypeKey, @NotNull BigDecimal value) {
    }

    public void serverShutdown() {
    }

    public void serverStartup() {
    }
}
