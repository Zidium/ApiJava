package zidium.logs;

import java.util.Date;
import zidium.client.IZidiumClient;
import zidium.webServices.IZidiumTransport;
import zidium.components.IComponentControl;
import zidium.dto.getLogConfig.GetLogConfigResponse;


public class LogWrapper implements ILog {

    private ILog _log = FakeLog.Instance;
    private Date _lastGetTime;
    private final IComponentControl _componentControl;
    private final IZidiumClient _client;
    
    public LogWrapper(
            IComponentControl componentControl, 
            IZidiumClient client){
        _componentControl = componentControl;
        _client = client;
    }
    
    private boolean canLoad(){
        if (_lastGetTime==null){
            return true;
        }
        long time = new Date().getTime() - _lastGetTime.getTime();
        return time > 1000 * 60; // прошло более минуты
    }
    
    private ILog loadLog() {
        try{
            if (_componentControl.isFake()){
                return null;
            }
            String componentId = _componentControl.getId();
            if (componentId==null){
                return null;
            }
            IZidiumTransport transport = _client.getTransport();
            GetLogConfigResponse response = transport.getLogConfig(componentId);
            if (response.success()){
                LogQueue logQueue = _client.getLogManager().createLogQueue(_componentControl);
                return new Log(_client, _componentControl, response.Data, logQueue);
            }
        }
        catch(Exception e){
            
        }
        return null;
    }
    
    private ILog getLog() {
        if (_log.isFake() && canLoad()){
            _lastGetTime = new Date();
            ILog log = loadLog();
            if (log!=null && log.isFake()==false){
                _log = log;
            }
        }
        return _log;
    }
    
    @Override
    public boolean isTraceEnabled() {
        ILog log = getLog();
        return log.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        ILog log = getLog();
        return log.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        ILog log = getLog();
        return log.isInfoEnabled();
    }

    @Override
    public boolean isWarningEnabled() {
        ILog log = getLog();
        return log.isWarningEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        ILog log = getLog();
        return log.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        ILog log = getLog();
        return log.isFatalEnabled();
    }

    @Override
    public void trace(String message) {
        ILog log = getLog();
        log.trace(message);
    }

    @Override
    public void trace(String message, Exception exception) {
        ILog log = getLog();
        log.trace(message, exception);
    }

    @Override
    public void debug(String message) {
        ILog log = getLog();
        log.debug(message);
    }

    @Override
    public void debug(String message, Exception exception) {
        ILog log = getLog();
        log.debug(message, exception);
    }

    @Override
    public void info(String message) {
        ILog log = getLog();
        log.info(message);
    }

    @Override
    public void info(String message, Exception exception) {
        ILog log = getLog();
        log.info(message, exception);
    }

    @Override
    public void warning(String message) {
        ILog log = getLog();
        log.warning(message);
    }

    @Override
    public void warning(String message, Exception exception) {
        ILog log = getLog();
        log.warning(message, exception);
    }

    @Override
    public void error(String message) {
        ILog log = getLog();
        log.error(message);
    }

    @Override
    public void error(String message, Exception exception) {
        ILog log = getLog();
        log.error(message, exception);
    }

    @Override
    public void fatal(String message) {
        ILog log = getLog();
        log.fatal(message);
    }

    @Override
    public void fatal(String message, Exception exception) {
        ILog log = getLog();
        log.fatal(message, exception);
    }

    @Override
    public boolean isFake() {
        ILog log = getLog();
        return log.isFake();
    }    

    @Override
    public boolean reloadConfig() {
        ILog log = getLog();
        return log.reloadConfig();
    }

    @Override
    public long getQueueSize() {
        ILog log = getLog();
        return log.getQueueSize(); 
    }
}
