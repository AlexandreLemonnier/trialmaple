package com.trialmaple.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerWrapper {
    private Logger logger;

    public LoggerWrapper(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void debug(String message, Object... parameters) {
        if (logger.isDebugEnabled()) {
            logger.debug(getFullMessage(message, parameters));
        }
    }

    public void info(String message, Object... parameters) {
        if (logger.isInfoEnabled()) {
            logger.info(getFullMessage(message, parameters));
        }
    }

    public void error(String message, Throwable e, Object... parameters) {
        if (logger.isErrorEnabled()) {
            logger.error(getFullMessage(message, parameters), e);
        }
    }

    public void error(String message, Object... parameters) {
        if (logger.isErrorEnabled()) {
            logger.error(getFullMessage(message, parameters));
        }
    }

    public void warn(String message, Throwable e, Object... parameters) {
        if (logger.isWarnEnabled()) {
            logger.warn(getFullMessage(message, parameters), e);
        }
    }

    public void warn(String message, Object... parameters) {
        if (logger.isWarnEnabled()) {
            logger.warn(getFullMessage(message, parameters));
        }
    }

    private String getFullMessage(String message, Object... parameters) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (Object param : parameters) {
            if (i%2 == 0) {
                res.append("[").append(param).append("]");
            } else {
                res.append("[").append(param).append("]-");
            }
        }
        return res.append(" ").append(message).toString();
    }
}
