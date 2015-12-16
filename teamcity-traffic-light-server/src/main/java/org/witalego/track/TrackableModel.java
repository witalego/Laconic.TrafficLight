package org.witalego.track;

public class TrackableModel<T>
{
    public T oldValue;
    public T newValue;

    public TrackableModel(T oldValue, T newValue)
    {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Boolean isChanged()
    {
        return !oldValue.equals(newValue);
    }
}