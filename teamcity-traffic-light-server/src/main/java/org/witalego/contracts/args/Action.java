package org.witalego.contracts.args;

public interface Action<TParameter>
{
    void invoke(TParameter parameter);
}