package zidium.logs;

import java.util.concurrent.atomic.AtomicInteger;
import zidium.helpers.ExceptionHelper;
import zidium.client.ITimeService;
import zidium.client.IZidiumClient;
import zidium.components.IComponentControl;
import zidium.dto.getLogConfig.GetLogConfigResponse;
import zidium.dto.getLogConfig.GetLogConfigResponseData;
import zidium.webServices.IZidiumTransport;


public class Log implements ILog {

    private final IZidiumClient _client;
    private final IComponentControl _componentControl;    
    private final AtomicInteger _order = new AtomicInteger();
    private final LogQueue _queue;
    
    private GetLogConfigResponseData _config;
    
    public Log(IZidiumClient client, 
            IComponentControl componentControl, 
            GetLogConfigResponseData config,
            LogQueue queue){
        _client = client;
        _componentControl = componentControl;
        _config = config;
        _queue = queue;
    }
    
    
    @Override
    public boolean isTraceEnabled() {
        return _config.IsTraceEnabled;
    }

    @Override
    public boolean isDebugEnabled() {
        return _config.IsDebugEnabled;
    }

    @Override
    public boolean isInfoEnabled() {
        return _config.IsInfoEnabled;
    }

    @Override
    public boolean isWarningEnabled() {
        return _config.IsWarningEnabled;
    }

    @Override
    public boolean isErrorEnabled() {
        return _config.IsErrorEnabled;
    }

    @Override
    public boolean isFatalEnabled() {
        return _config.IsFatalEnabled;
    }
    
    private void addLogMessage(String level, String message){
        addLogMessage(level, message, null);
    }

    private void addLogMessage(String level, String message, Exception exception){
       
        if (LogLevel.TRACE.equals(level) && isTraceEnabled()==false){
            return;
        }
        if (LogLevel.DEBUG.equals(level) && isDebugEnabled()==false){
            return;
        }
        if (LogLevel.INFO.equals(level) && isInfoEnabled()==false){
            return;
        }
        if (LogLevel.WARNING.equals(level) && isWarningEnabled()==false){
            return;
        }
        if (LogLevel.ERROR.equals(level) && isErrorEnabled()==false){
            return;
        }
        if (LogLevel.FATAL.equals(level) && isFatalEnabled()==false){
            return;
        }
        
        ITimeService timeService = _client.getTimeService();
        ILogManager logManager = _client.getLogManager();
        
        LogMessage logMessage = new LogMessage();
        logMessage.Message = message;
        logMessage.ComponentControl = _componentControl;
        logMessage.Level = level;
        logMessage.Order = _order.incrementAndGet();        
        logMessage.Time = timeService.getNow();
        
        if (exception!=null){
            String stack = ExceptionHelper.getStackTraceAsString(exception);
            logMessage.getProperties().addString("Stack", stack);
        }
        _queue.addToEnd(logMessage);
        _client.getLogManager().beginProcessQueues();
    }
    
    @Override
    public void trace(String message) {
        addLogMessage(LogLevel.TRACE, message);
    }

    @Override
    public void trace(String message, Exception exception) {
        addLogMessage(LogLevel.TRACE, message, exception);
    }

    @Override
    public void debug(String message) {
        addLogMessage(LogLevel.DEBUG, message);
    }

    @Override
    public void debug(String message, Exception exception) {
        addLogMessage(LogLevel.DEBUG, message, exception);
    }

    @Override
    public void info(String message) {
        addLogMessage(LogLevel.INFO, message);
    }

    @Override
    public void info(String message, Exception exception) {
        addLogMessage(LogLevel.INFO, message, exception);
    }

    @Override
    public void warning(String message) {
        addLogMessage(LogLevel.WARNING, message);
    }

    @Override
    public void warning(String message, Exception exception) {
        addLogMessage(LogLevel.WARNING, message, exception);
    }

    @Override
    public void error(String message) {
        addLogMessage(LogLevel.ERROR, message);
    }

    @Override
    public void error(String message, Exception exception) {
        addLogMessage(LogLevel.ERROR, message, exception);
    }

    @Override
    public void fatal(String message) {
        addLogMessage(LogLevel.FATAL, message);
    }

    @Override
    public void fatal(String message, Exception exception) {
        addLogMessage(LogLevel.FATAL, message, exception);
    }

    @Override
    public boolean isFake() {
        return false;
    }    

    @Override
    public boolean reloadConfig() {
        IZidiumTransport transport = _client.getTransport();
        String componentId = _componentControl.getId();
        GetLogConfigResponse response = transport.getLogConfig(componentId);
        if (response.success()){
            _config = response.Data;
            return true;
        }
        return false;
    }

    @Override
    public long getQueueSize() {
        return _queue.getSize();
    }
}
