package zidium.events;

import java.util.ArrayList;
import zidium.threads.SingleThreadJob;
import zidium.components.IComponentControl;
import zidium.client.IZidiumClient;
import zidium.webServices.IZidiumTransport;
import zidium.dto.sendEvent.SendEventRequestData;


public class EventManager implements IEventManager{

    private final IZidiumClient _client;
    private ArrayList<ZidiumEvent> _events = new ArrayList<>();
    private final Object _lock = new Object();
    private int _maxCount = 1000;
    private final SingleThreadJob _processEventsJob;
    
    public EventManager(IZidiumClient client){
        _client = client;
        _processEventsJob = new SingleThreadJob(this::processEvents);
    }
    
    private void processEvents(){
        
        // события, которые мы еще не можем отправить
        ArrayList<ZidiumEvent> offlineEvents = new ArrayList<>();
        
        // события, которые можем отправить сейчас
        ArrayList<ZidiumEvent> onlineEvents = new ArrayList<>();
        
        // сортируем события
        synchronized(_lock){
            for (ZidiumEvent event : _events) {
                if (event.getComponentControl().isFake()){
                    offlineEvents.add(event);
                }
                else{
                    onlineEvents.add(event);
                }
            }
            _events = offlineEvents;
        }
        
        // события, которые не удалось отправить
        ArrayList<ZidiumEvent> errorEvents = new ArrayList<>();
        
        // отправляем события
        IZidiumTransport transport = _client.getTransport();
        for (ZidiumEvent event : onlineEvents) {
            try{
                IComponentControl componentControl = event.getComponentControl();
                
                SendEventRequestData eventData = new SendEventRequestData();
                eventData.Category = "ApplicationError";
                eventData.ComponentId = componentControl.getId();
                eventData.Count = 1;
                eventData.Importance = event.getImportance();
                eventData.Message = event.getMessage();
                eventData.StartDate = event.getDate();
                eventData.TypeCode = event.getTypeCode();
                eventData.TypeDisplayName = event.getTypeDisplayName();
                eventData.TypeSystemName = event.getTypeSystemName();
                eventData.Properties = event.getPropertis().getAll();
                
                transport.sendEvent(eventData);
            }
            catch(Exception ex){
                errorEvents.add(event);
                // хз
            }
        }
    }
    
    @Override
    public void add(ZidiumEvent event) {
        synchronized(_lock){
            if (_events.size()>_maxCount){
                _events.clear();
            }
            _events.add(event);
        }
        _processEventsJob.beginDo();
    }

    @Override
    public void flush() {
        _processEventsJob.doSync();
    }    

    @Override
    public void setMaxEventsCount(int count) {
        _maxCount = count;
    }

    @Override
    public int getMaxEventsCount() {
        return _maxCount;
    }
}
