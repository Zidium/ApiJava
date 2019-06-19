package zidium.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zidium.helpers.StackHelper;


public class TimeoutTask {
    
    private final int _maxTimeSeconds;
    private Thread _thread; 
    private StackTraceElement[] _timeoutStack;
    private Logger _logger = LogManager.getLogger("TimeoutTask");
        
    public TimeoutTask(int maxTimeSeconds){
        _maxTimeSeconds = maxTimeSeconds;
    }
    
    public boolean isTimeout(){
        return _timeoutStack!=null;
    }

    
    public void setLogger(Logger logger){
        _logger = logger;
    }
    
    public String getStackString(){
        StackTraceElement[] stackTraceElements = _timeoutStack;
        if (stackTraceElements!=null){
            return StackHelper.toString(stackTraceElements);
        }
        return null;
    }
    
    public StackTraceElement[] getTimeoutStackTraceElements(){
        return _timeoutStack;
    }
    
    public void doWork(ITimeoutTaskAction action) {
        Thread thread = new Thread(() -> {
            try{
                action.doWork();
            }
            catch(Exception ex){
                _logger.error("timeoutTask error: " + ex.getMessage(), ex);
            }
            finally{
                _thread = null;
            }
        });
        _thread = thread;
        _timeoutStack = null;
        thread.start();
        int sleep = 10;
        int current = 0;
        int maxTime = _maxTimeSeconds * 1000;
        while (current < maxTime) {
            try{
                Thread.sleep(sleep);
            }
            catch(Exception e){
                _logger.error("sleep error: " + e.getMessage());
            }
            current += sleep;
            if (_thread==null){
                return;
            }            
        }
        _timeoutStack = thread.getStackTrace();
        String stack = StackHelper.toString(_timeoutStack);
        _logger.error("task timeout");
        _logger.error("stack: " + System.lineSeparator() + stack);
    }
}
