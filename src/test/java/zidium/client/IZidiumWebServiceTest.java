package zidium.client;

import java.util.Date;
import zidium.webServices.IZidiumWebService;
import zidium.webServices.ZidiumWebService;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import zidium.dto.getEcho.GetEchoRequest;
import zidium.dto.getEcho.GetEchoRequestData;
import zidium.dto.getEcho.GetEchoResponse;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequest;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.getOrAddComponent.GetOrAddComponentResponse;
import zidium.dto.sendEvent.SendEventRequest;
import zidium.dto.sendEvent.SendEventRequestData;
import zidium.dto.sendEvent.SendEventResponse;
import zidium.dto.Token;
import zidium.dto.sendLogs.LogMessageDto;
import zidium.dto.sendLogs.SendLogsRequest;
import zidium.dto.sendLogs.SendLogsResponse;
import zidium.logs.LogLevel;


public class IZidiumWebServiceTest {
    
    private IZidiumWebService _service;
    private Token _token;
    private String _containerComponentId = "cdad8940-2ebf-48c5-a50d-3423540d3b25"; // контейнер для всех устройств alcospot-tr
    
    public IZidiumWebServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        String url = "http://test.api.zidium.net/1.0/";
        _service = new ZidiumWebService(url);
        _token = new Token();
        _token.Account = "test";
        _token.SecretKey = "228a4c7c-84cd-467d-9c01-22bb08bb1b4f";
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testSendLogs() throws Exception {
        System.out.println("sendEvent");
        SendLogsRequest request = new SendLogsRequest();
        request.Token = _token;
        LogMessageDto logMessage = new LogMessageDto();
        logMessage.ComponentId = _containerComponentId;
        logMessage.Date = new Date();
        logMessage.Level = LogLevel.INFO;
        logMessage.Message = "test";
        logMessage.Order = 1;
        request.Data = new LogMessageDto[]{logMessage};       
        SendLogsResponse response = _service.sendLogs(request);
        assertTrue(response.success());
    }
    
    @Test
    public void testSendEvent() throws Exception {
        System.out.println("sendEvent");
        SendEventRequest request = new SendEventRequest();
        request.Token = _token;
        request.Data = new SendEventRequestData();
        request.Data.Category = "ApplicationError";
        request.Data.ComponentId = _containerComponentId;
        request.Data.TypeSystemName = "test error";
        SendEventResponse response = _service.sendEvent(request);
        assertTrue(response.success());
    }

    
    @Test
    public void testGetEcho() throws Exception {
        System.out.println("getEcho");
        GetEchoRequest request = new GetEchoRequest();
        request.Token = _token;
        request.Data = new GetEchoRequestData();
        request.Data.Message = "123";
        GetEchoResponse response = _service.getEcho(request);
        assertTrue(response.success()); 
        assertEquals("123", response.Data);
    }
    
    
    @Test
    public void testGetOrAddComponent() throws Exception {
        System.out.println("getOrAddComponent");
        GetOrAddComponentRequest request = new GetOrAddComponentRequest();
        request.Token = _token;
        request.Data = new GetOrAddComponentRequestData();
        request.Data.DisplayName = "AlcoSpot TR test";
        request.Data.SystemName = "AlcoSpotTR_" + UUID.randomUUID();
        request.Data.ParentComponentId = _containerComponentId;
        request.Data.TypeId = "685678f9-d778-45bd-8ad2-d11c80fdccad"; // разные
        GetOrAddComponentResponse response = _service.getOrAddComponent(request);
        assertTrue(response.success());
    } 
}
