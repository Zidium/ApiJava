package zidium.components;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import zidium.helpers.DateHelper;
import zidium.helpers.ExceptionHelper;
import zidium.helpers.GuidHelper;
import zidium.client.IZidiumClient;
import zidium.helpers.ZidiumTestHelper;
import zidium.dto.ExtentionPropertyDto;
import zidium.dto.getEvents.EventDto;
import zidium.dto.getEvents.GetEventsRequestData;
import zidium.dto.getEvents.GetEventsResponse;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.sendMetric.SendMetricRequestData;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.logs.ILog;

public class IComponentControlTest {
    
    public IComponentControlTest() {
    }  
    
    private IZidiumClient getTestClient(){
        return ZidiumTestHelper.getTestClient();
    }
    
    @Test
    public void testGetId() {
        System.out.println("getId");
        
        // root
        IZidiumClient client = getTestClient();
        IComponentControl root = client.getRootComponentControl();
        assertFalse(root.isFake());
        assertNotNull(root.getId());
        
        // by id
        String componentId = ZidiumTestHelper.getContainerComponentId(client);
        IComponentControl component = client.getComponentControl(componentId);
        assertFalse(component.isFake());
        assertEquals(componentId, component.getId());
        
        // invalid id
        component = client.getComponentControl("invalid-id");
        assertTrue(component.isFake());
        assertEquals("invalid-id", component.getId());
        
        // null
        component = client.getComponentControl(new IComponentIdProvider() {
            @Override
            public String getComponentId() {
                return null;
            }
        });
        assertTrue(component.isFake());
        assertNull(component.getId());
    }

    @Test
    public void testIsFake() {
        System.out.println("isFake");
        
        // root
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        assertFalse(component.isFake());
        
        // invalid-id
        component = client.getComponentControl("invalid-id");
        assertTrue(component.isFake());
        
        // null
        component = client.getComponentControl(new IComponentIdProvider() {
            @Override
            public String getComponentId() {
                return null;
            }
        });
        assertTrue(component.isFake());
    }
    
    private EventDto validateEventExists(GetEventsResponse response, String message) throws Exception{
        if (response.success()==false){
            throw new Exception("GetEventsResponse failed");
        }
        for (EventDto eventDto : response.Data) {
            if (message.equals(eventDto.Message)){
                return eventDto;
            }
        }
        throw new Exception("event not found");
    }
    
    @Test
    public void testAddError_String() throws Exception {
        System.out.println("addError");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        Date now = new Date();
        String message = "test error " + GuidHelper.getRandom();
        component.addError(message);
        client.getEventManager().flush();
        GetEventsRequestData filter = new GetEventsRequestData();
        filter.OwnerId = component.getId();
        filter.Category = "ApplicationError";
        filter.From = DateHelper.addSeconds(now, -600);
        GetEventsResponse response = client.getTransport().getEvents(filter);
        validateEventExists(response, message);
    }
    
    private Exception getCatchedException(){
        try{
            int val = Integer.parseInt("dsd");
        }
        catch(Exception e){
            return e;
        }
        return null;
    }
    
    @Test
    public void testAddError_String_Exception() throws Exception {
        System.out.println("addError");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        Date now = new Date();
        
        // catched exception
        String message = "test error " + GuidHelper.getRandom();
        Exception exception = getCatchedException();
        component.addError(message, exception);
        client.getEventManager().flush();
        GetEventsRequestData filter = new GetEventsRequestData();
        filter.OwnerId = component.getId();
        filter.Category = "ApplicationError";
        filter.From = DateHelper.addSeconds(now, -600);
        GetEventsResponse response = client.getTransport().getEvents(filter);
        EventDto event = validateEventExists(response, message);
        ExtentionPropertyDto stackProperty = ZidiumTestHelper.getStackProperty(event.Properties);
        assertNotNull(stackProperty);
        String stack = ExceptionHelper.getStackTraceAsString(exception);
        assertEquals(stack, stackProperty.Value);
    }

    @Test
    public void testGetLog() {
        System.out.println("getLog");
        
        // root
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertFalse(log.isFake());
        
        // by id
        String componentId = ZidiumTestHelper.getContainerComponentId(client);
        component = client.getComponentControl(componentId);
        log = component.getLog();
        assertFalse(log.isFake());
        
        // invalid-id
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertTrue(log.isFake());
        
        // null
        component = client.getComponentControl(new IComponentIdProvider() {
            @Override
            public String getComponentId() {
                return null;
            }
        });
        log = component.getLog();
        assertTrue(log.isFake());
    }
    
    @Test
    public void testGetOrCreateChild() {
        System.out.println("getOrCreateChild");
        IComponentControl parent = ZidiumTestHelper.getContainerComponentControl();
        GetOrAddComponentRequestData data = new GetOrAddComponentRequestData();
        data.SystemName = "test child " + GuidHelper.getRandom();
        data.TypeId = "685678f9-d778-45bd-8ad2-d11c80fdccad";
        IComponentControl newChild = parent.getOrCreateChild(data);
        assertFalse(newChild.isFake());
    }    
    
    @Test
    public void testSendMetric() {
        System.out.println("sendMetric");
        IZidiumClient client = ZidiumTestHelper.getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        assertFalse(component.isFake());
        SendMetricRequestData data = new SendMetricRequestData();
        data.Name = "x";
        data.Value = 100.123;
        data.ActualIntervalSecs = 60;
        SendMetricResponse response = component.sendMetric(data);
        assertTrue(response.success());
    }   
}
