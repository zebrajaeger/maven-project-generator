package de.zebrajaeger.maven.projectgenerator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoggingUtils {

    private static Class<?> loggerFactoryClass;
    private static Class<?> loggerClass;

    private Object logger;
    private Method traceMethod;
    private Method infoMethod;
    private Method debugMethod;
    private Method warnMethod;
    private Method errorMethod;

    public static LoggingUtils of(String name) {
        return new LoggingUtils(name);
    }

    public static LoggingUtils of(Class<?> clazz) {
        return of(clazz.getName());
    }

    private static Method getLoggerMethod(String type) throws NoSuchMethodException, ClassNotFoundException {
        return getLoggerClass().getDeclaredMethod(type, String.class, Object[].class);
    }

    private static Class<?> getLoggerClass() throws ClassNotFoundException {
        if (loggerClass == null) {
            loggerClass = Thread.currentThread().getContextClassLoader().loadClass(Logger.class.getName());
        }
        return loggerClass;
    }

    private LoggingUtils(String name) {
        logger = getLogger(name);
    }

    private Object getLogger(String name) {
        try {
            if (loggerFactoryClass == null) {
                loggerFactoryClass = Thread.currentThread().getContextClassLoader().loadClass(LoggerFactory.class.getName());
            }
            return loggerFactoryClass.getDeclaredMethod("getLogger", String.class).invoke(null, name);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException(String.format("Unable to get logger for: '%s'", name), e);
        }
    }

    public void trace(String msg, Object... params) {
        try {
            if (traceMethod == null) {
                traceMethod = getLoggerMethod("info");
            }
            traceMethod.invoke(logger, msg, params);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to call Logger.trace(...)", e);
        }
    }

    public void info(String msg, Object... params) {
        try {
            if (infoMethod == null) {
                infoMethod = getLoggerMethod("info");
            }
            infoMethod.invoke(logger, msg, params);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to call Logger.info(...)", e);
        }
    }

    public void debug(String msg, Object... params) {
        try {
            if (debugMethod == null) {
                debugMethod = getLoggerMethod("debug");
            }
            debugMethod.invoke(logger, msg, params);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to call Logger.debug(...)", e);
        }
    }

    public void warn(String msg, Object... params) {
        try {
            if (warnMethod == null) {
                warnMethod = getLoggerMethod("warnwarn");
            }
            warnMethod.invoke(logger, msg, params);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to call Logger.warn(...)", e);
        }
    }

    public void error(String msg, Object... params) {
        try {
            if (errorMethod == null) {
                errorMethod = getLoggerMethod("error");
            }
            errorMethod.invoke(logger, msg, params);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to call Logger.error(...)", e);
        }
    }
}
