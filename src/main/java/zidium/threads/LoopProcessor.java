package zidium.threads;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zidium.helpers.DateHelper;

/**
 * Выполняет одно и тоже действие в бесконечном цикле
 */
public class LoopProcessor implements Closeable{
    
    private LoopProcessorAction _action;
    private boolean _isStoped = false;
    private Thread _thread;
    private int _period;
    private long _executions = 0;
    private Date _lastRunTime;
    private final CancelationToken _cancelationToken;
    
    private Logger _logger = LogManager.getLogger("LoopProcessor");   
    
    public LoopProcessor(LoopProcessorAction action, int period, CancelationToken cancelationToken){
        _action = action;
        _period = period;
        _cancelationToken = cancelationToken;
    }
    
    public void setLogger(Logger logger){
        _logger = logger;
    }
    
    public long getExecutions(){
        return _executions;
    }
    
    public long resetExecutions(){
        long executions = _executions;
        _executions = 0;
        return executions;
    }
    
    private void doWork(){
        try{
            _lastRunTime = new Date();
            _executions++;
            _action.doWork();
        }
        catch(Exception e){
            _logger.error("doWork error", e);
        }
    }
    
    public Date getLastRunTime(){
        return _lastRunTime;
    }
    
    private void trySleep(int time) {
        int current = 0;
        int sleep = 10;
        while (current < time ) {            
            try {
                Thread.sleep(sleep);
                current += sleep;
                if (canRun()==false) {
                    return;
                }
            } catch (InterruptedException ex) {              
            }
        }
    }
    
    public String getDebugRuntimeInfo(){        
        if (_lastRunTime==null){
            return "lastRunTime is null";
        }
        String result = "lastRunTime: " + DateHelper.getRussianDateTimeString(_lastRunTime) + System.lineSeparator();
        if (_thread==null){
            result += "thread is null";
        }
        else{
            result += "thread state: " + _thread.getState() + System.lineSeparator();
            StackTraceElement[] stackTraceElements = _thread.getStackTrace();
            if (stackTraceElements!=null){
                result += "thread stackTraceElements:" + System.lineSeparator();
                for (StackTraceElement stackTraceElement : stackTraceElements) {
                    result += " " + stackTraceElement;
                }
            }
        }        
        return result;
    }
    
    public boolean canRun(){
        return (_isStoped==false) && (_cancelationToken.isCanceled() == false);
    }
    
    private void loop(){
        try{
            while (canRun()) {
                // выполним работу
                Date start = new Date();
                doWork();
                if (canRun()==false){
                    return;
                }

                // заснем, если осталось время
                Date end = new Date();
                int duration = (int)(end.getTime() - start.getTime());
                int sleep = _period - duration;
                if (sleep>0){
                    trySleep(sleep);
                }
            }            
        }
        finally{
            _thread = null;
            _logger.debug("exit loop");
        }        
    }
    
    public void start() throws Exception{
        if (_thread!=null){
            throw new Exception("thread is not null");
        }
        if (_isStoped){
            throw new Exception("loop is stoped");
        }
        Thread thread = new Thread(() -> {
            loop();
        });
        thread.start();
        _thread = thread;
    }
    
    public void beginStop(){
        _isStoped = true;
    }
    
    public boolean isStoped(){
        return _thread==null;
    }
    
    public boolean waitStoped(int timeout) {
        int time = 0;
        int sleep = 25;
        while (isStoped()==false) {            
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                _logger.error("InterruptedException");
                return false;
            }
            time += sleep;
            if (time > timeout){
                _logger.info("stop loop timeout");
                return true;
            }
        }
        _logger.info("loop stoped success");
        return false;
    }

    @Override
    public void close() throws IOException {
        _isStoped = true;
    }
}
