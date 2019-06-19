package zidium.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Задача выполняется всегда один потоком
 */
public class SingleThreadJob {
    
    private static final Logger _logger = LogManager.getLogger(SingleThreadJob.class);
    private boolean _canRun = false;
    private final Object _lock = new Object();
    private final Runnable _action;
    private Thread _thread;
    
    public SingleThreadJob(Runnable action){
        _action = action;
    }
    
    public boolean isRunned(){
        return _thread!=null;
    }
    
    public void beginDo(){
        synchronized(_lock){            
            if (_thread==null){
                Thread thread = new Thread(this::doWrapper);
                thread.start();
                _thread = thread;
            }
            _canRun = true;
        }
    }
    
    public void doSync(){
        doWrapper();
    }
    
    private synchronized void doWrapper(){
        try{
            _thread = Thread.currentThread();
            while (true) {            
                try{
                    synchronized(_lock){
                        if (_canRun==false){
                            return;
                        }
                        _canRun = false;
                    }
                    _action.run();
                }
                catch(Exception exception){
                    _logger.error("doWrapper error", exception);
                }
            }
        }
        finally{
            synchronized(_lock){
                _thread = null;
                if (_canRun){
                    beginDo();
                }
            }
        }        
    }
}
