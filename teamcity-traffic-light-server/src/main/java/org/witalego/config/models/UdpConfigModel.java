package org.witalego.config.models;

import org.witalego.track.TrackableModel;

public class UdpConfigModel
{
    public TrackableModel<Integer> port;
    public TrackableModel<String> ipAddress;
    public TrackableModel<Integer> period;
}