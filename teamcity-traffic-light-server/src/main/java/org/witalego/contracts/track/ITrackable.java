package org.witalego.contracts.track;

public interface ITrackable
{
    Boolean isChanged();
    void save();
}