package za.co.seamlesspay.v1.util.EmvUtil.log;

public interface LogWriter {
    void debug(Class clazz, String msg);

    void error(Class clazz, String msg, Throwable t);

    void error(Class clazz, String msg);

    void info(Class clazz, String msg);

    boolean isDebugEnabled();
}
