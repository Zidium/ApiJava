package zidium.logs;

import java.util.ArrayList;
import java.util.List;
import zidium.client.ITimeService;
import zidium.components.IComponentControl;


public class LogQueue {
    
    private final IComponentControl _componentControl;    
    private final Object _lock = new Object();
    private final ILogManager _logManager;
    private final ITimeService _timeService;
    
    private ArrayList<LogMessage> _logs = new ArrayList<>();
    
    public LogQueue(
            ILogManager logManager, 
            IComponentControl componentControl,
            ITimeService timeService){
        _componentControl = componentControl;
        _logManager = logManager;
        _timeService = timeService;
    }
    
    public IComponentControl getComponentControl(){
        return _componentControl;
    }
    
    private void validateSize(){
        if (_logs.size() > _logManager.getMaxEventsCount()){
            synchronized(_lock){
                _logs.clear();
                LogMessage log = new LogMessage();
                log.ComponentControl = _componentControl;
                log.Level = LogLevel.INFO;
                log.Message = "# clear log queue (zidium api)";
                log.Time = _timeService.getNow();
                _logs.add(log);
            }
        }
    }
    
    public void addToEnd(LogMessage log){
        synchronized(_lock){
            _logs.add(log);
        }
    }
    
    public void addToBegin(List<LogMessage> logs){
        synchronized(_lock){
            ArrayList<LogMessage> newLogs = new ArrayList<>();
            newLogs.addAll(logs);
            newLogs.addAll(_logs);
            _logs = newLogs;
        }
    }
    
    public List<LogMessage> getAllAndClear(){
        synchronized(_lock){
            List<LogMessage> result = _logs;
            _logs = new ArrayList<>();
            return result;
        }
    }
    
    public long getSize(){
        return _logs.size();
    }
}
