package io.sponges.dubtrack4j.util;

import io.sponges.dubtrack4j.DubtrackAPIImpl;

/**
 * Util class for logging
 *
 * Why use this instead of a logging lib?
 * Those are really heavy for performing a simple task & I want to leave logging options open to those who implement
 * the api instead of it being part of the api
 */
public class Logger {

    // TODO add timestamp for log messages & prefix with DT4J?

    public enum LoggingMode {
        WARNING, NORMAL, DEBUG
    }

    public static void debug(String msg) {
        if (DubtrackAPIImpl.getLoggingMode() == LoggingMode.DEBUG) {
            System.out.println("DEBUG> " + msg);
        }
    }

    public static void info(String msg) {
        if (DubtrackAPIImpl.getLoggingMode() != LoggingMode.WARNING) {
            System.out.println("INFO> " + msg);
        }
    }

    public static void warning(String msg) {
        System.out.println("WARNING> " + msg);
    }

}
