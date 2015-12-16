package org.witalego.config.models;

import org.witalego.config.ProtocolType;
import org.witalego.track.TrackableModel;

public class MainConfigModel
{
    public TrackableModel<Boolean> enabled;
    public TrackableModel<ProtocolType> protocolType;
}