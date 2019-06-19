package zidium.logs;


public class FakeLog implements ILog{
    
    public static final ILog Instance = new FakeLog();
    
    private FakeLog(){        
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarningEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isFatalEnabled() {
        return false;
    }

    @Override
    public void trace(String message) {
    }

    @Override
    public void trace(String message, Exception exception) {
    }

    @Override
    public void debug(String message) {
    }

    @Override
    public void debug(String message, Exception exception) {
    }

    @Override
    public void info(String message) {
    }

    @Override
    public void info(String message, Exception exception) {
    }

    @Override
    public void warning(String message) {
    }

    @Override
    public void warning(String message, Exception exception) {
    }

    @Override
    public void error(String message) {
    }

    @Override
    public void error(String message, Exception exception) {
    }

    @Override
    public void fatal(String message) {
    }

    @Override
    public void fatal(String message, Exception exception) {
    }

    @Override
    public boolean isFake() {
        return true;
    }    

    @Override
    public boolean reloadConfig() {
        return false;
    }

    @Override
    public long getQueueSize() {
        return 0;
    }
}
