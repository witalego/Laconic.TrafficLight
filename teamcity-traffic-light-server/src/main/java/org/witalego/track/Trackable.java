package org.witalego.track;

import org.witalego.contracts.track.ITrackable;

public class Trackable<T> implements ITrackable
{
    private T _value;
    private T _newValue;

    public Trackable(T value)
    {
        _value = value;
        _newValue = value;
    }

    public Boolean isChanged()
    {
        return !_value.equals(_newValue);
    }

    @Override
    public void save()
    {
        if (isChanged())
        {
            _value = _newValue;
        }
    }

    public T get()
    {
        return _value;
    }

    public void set(T value)
    {
        _newValue = value;
    }

    public TrackableModel<T> getModel()
    {
        return new TrackableModel<>(_value, _newValue);
    }

    @Override
    public String toString()
    {
        return _newValue.toString();
    }
}