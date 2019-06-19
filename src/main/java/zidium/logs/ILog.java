package zidium.logs;


public interface ILog {
    
    public boolean isFake();
    
    public boolean isTraceEnabled();

    public boolean isDebugEnabled();

    public boolean isInfoEnabled();

    public boolean isWarningEnabled();

    public boolean isErrorEnabled();

    public boolean isFatalEnabled();
    
    public void trace(String message);
    
    public void trace(String message, Exception exception);
    
    public void debug(String message);
    
    public void debug(String message, Exception exception);
    
    public void info(String message);
    
    public void info(String message, Exception exception);
    
    public void warning(String message);
    
    public void warning(String message, Exception exception);
    
    public void error(String message);
    
    public void error(String message, Exception exception);
    
    public void fatal(String message);
    
    public void fatal(String message, Exception exception);
    
    public boolean reloadConfig();
    
    public long getQueueSize();
}
