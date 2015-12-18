package org.witalego.contracts.logging;

public interface ILog
{
    void info(String s);
    void info(String s, Object... args);
    void warning(String s);
    void error(String s, Throwable e);
}