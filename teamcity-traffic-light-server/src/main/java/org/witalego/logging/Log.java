package org.witalego.logging;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;
import org.witalego.contracts.logging.ILog;

public class Log implements ILog
{
    private final String _prefix = "TrafficLightServer";
    private Logger _logger = Loggers.SERVER;

    public void info(String s)
    {
        _logger.info(formatMessage(s));
    }

    @Override
    public void info(String s, Object... args)
    {
        info(String.format(s, args));
    }

    public void error(String s, Throwable e)
    {
        _logger.error(formatMessage(s), e);
    }

    private String formatMessage(String s)
    {
        return _prefix + ": " + s;
    }
}