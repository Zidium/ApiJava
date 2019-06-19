package zidium.logs;

import java.util.ArrayList;
import java.util.List;
import zidium.threads.SingleThreadJob;
import zidium.client.ITimeService;
import zidium.client.IZidiumClient;
import zidium.components.IComponentControl;
import zidium.webServices.IZidiumTransport;
import zidium.dto.sendLogs.LogMessageDto;
import zidium.dto.sendLogs.SendLogsResponse;


public class LogManager implements ILogManager{

    private final IZidiumClient _client;
    private ArrayList<LogQueue> _logQueues = new ArrayList<>();
    private final Object _lock = new Object();
    private int _maxCount = 1000;
    private final SingleThreadJob _processLogsJob;
    
    public LogManager(IZidiumClient client){
        _client = client;
        _processLogsJob = new SingleThreadJob(this::processLogs);
    }
    
    private void processLogs(){
        
        ArrayList<LogQueue> queues = new ArrayList<>();
        synchronized(_lock){
            queues.addAll(_logQueues);
        }
        for (LogQueue queue : queues) {
            processLogQueue(queue);
        }
    } 
    
    private void processLogQueue(LogQueue queue){
                
        IComponentControl component = queue.getComponentControl();
        if (component.isFake()){
            return;
        }
        
        // которые нужно отправить
        List<LogMessage> onlineItems = queue.getAllAndClear();
        
        // которые не удалось отправить
        ArrayList<LogMessage> errors = new ArrayList<>();
        
        // отправляем события
        int batchCount = 100; // количество сообщений в одном запросе АПИ
        ArrayList<LogMessage> batch = new ArrayList<LogMessage>();
        
        IZidiumTransport transport = _client.getTransport();
        for (LogMessage log : onlineItems) {
            batch.add(log);
            if (batch.size()==batchCount){
                sendBatch(batch, transport, errors);
            }
        }
        sendBatch(batch, transport, errors);
        
        // отправляем обратно в очередь, те, что не удалось отправить
        if (errors.size()>0){
            queue.addToBegin(errors);
        }
    }
    
    private void sendBatch(
            ArrayList<LogMessage> batch, 
            IZidiumTransport transport, 
            ArrayList<LogMessage> errors){
       
        if (batch.size()==0){
            return;
        }
        try{
            ArrayList<LogMessageDto> dtoList = new ArrayList<>();
            for (LogMessage logMessage : batch) {
                LogMessageDto dto = new LogMessageDto();
                dto.ComponentId = logMessage.ComponentControl.getId();
                dto.Date = logMessage.Time;
                dto.Level = logMessage.Level;
                dto.Message = logMessage.Message;
                dto.Order = logMessage.Order;
                dto.Properties = logMessage.getProperties().getAll();
                dtoList.add(dto);
            }
            LogMessageDto[] array = dtoList.toArray(new LogMessageDto[dtoList.size()]);  
            SendLogsResponse response = transport.sendLogs(array);
            if (response.success()){
                batch.clear();
                return;
            }            
        }
        catch(Exception ex){
            //todo
        }        
        
        for (LogMessage logMessage : batch) {
            errors.add(logMessage);
        }
    }
    
    @Override
    public void beginProcessQueues() {        
        _processLogsJob.beginDo();
    }

    @Override
    public void flush() {
         _processLogsJob.doSync();
    }

    @Override
    public void setMaxEventsCount(int count) {
        _maxCount = count;
    }

    @Override
    public int getMaxEventsCount() {
        return _maxCount;
    }

    @Override
    public LogQueue createLogQueue(IComponentControl componentControl) {
        ITimeService timeService = _client.getTimeService();
        LogQueue logQueue = new LogQueue(this, componentControl, timeService);
        synchronized(_lock){
            _logQueues.add(logQueue);
        }
        return logQueue;
    }    
}
