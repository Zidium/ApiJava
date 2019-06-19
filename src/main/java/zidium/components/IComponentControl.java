package zidium.components;

import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.sendMetric.SendMetricRequestData;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.updateComponent.UpdateComponentResponse;
import zidium.events.ZidiumEvent;
import zidium.logs.ILog;
import zidium.unitTestTypes.IUnitTestTypeControl;
import zidium.unitTests.IUnitTestControl;


public interface IComponentControl {
    
    public String getId();
    
    public boolean isFake();
    
    public void addError(String message);
    
    public void addError(String message, Throwable exception);
    
    public ZidiumEvent createError(String message);
    
    public ZidiumEvent createError(String message, Throwable exception);
    
    public void addEvent(ZidiumEvent event);
    
    public ILog getLog();
    
    public IComponentControl getOrCreateChild(String name);
    
    public IComponentControl getOrCreateChild(GetOrAddComponentRequestData data);
    
    public IUnitTestControl getOrCreateUnitTest(String name, IUnitTestTypeControl type);
    
    public SendMetricResponse sendMetric(SendMetricRequestData data);
    
    public UpdateComponentResponse update(UpdateComponentData data);
}
