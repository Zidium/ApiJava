package zidium.client;

import zidium.webServices.IZidiumTransport;
import zidium.events.IExceptionToEventConverter;
import zidium.events.IEventManager;
import zidium.components.IComponentControl;
import zidium.components.IComponentIdProvider;
import zidium.logs.ILogManager;
import zidium.unitTestTypes.IUnitTestTypeControl;
import zidium.unitTestTypes.IUnitTestTypeIdProvider;

public interface IZidiumClient {    
    
    public IComponentControl getDefaultComponentControl();
    public void setDefaultComponentControl(IComponentControl componentControl); 
    public IComponentControl setDefaultComponentControl(IComponentIdProvider provider);
    
    public IComponentControl getRootComponentControl();
    public IComponentControl getComponentControl(IComponentIdProvider provider);
    public IComponentControl getComponentControl(String id);
    
    public IUnitTestTypeControl getUnitTestType(IUnitTestTypeIdProvider provider);
    public IUnitTestTypeControl getOrCreateUnitTestType(String name);
    
    public IExceptionToEventConverter getExceptionToEventConverter();
    public void setExceptionToEventConverter(IExceptionToEventConverter converter);
    
    public ITimeService getTimeService();
    public IEventManager getEventManager();
    public ILogManager getLogManager();
    public IZidiumTransport getTransport();
}
