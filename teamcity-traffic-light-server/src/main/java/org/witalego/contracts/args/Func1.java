package org.witalego.contracts.args;

public interface Func1<TParameter, TResult>
{
    TResult invoke(TParameter parameter);
}