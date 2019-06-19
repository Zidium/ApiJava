package zidium.webServices;

import zidium.dto.getEcho.GetEchoResponse;
import zidium.dto.getLogConfig.GetLogConfigResponse;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.getOrAddComponent.GetOrAddComponentResponse;
import zidium.dto.GetServerTimeResponse;
import zidium.dto.Token;
import zidium.dto.getComponentById.GetComponentByIdResponse;
import zidium.dto.getEvents.GetEventsRequestData;
import zidium.dto.getEvents.GetEventsResponse;
import zidium.dto.getLogs.GetLogsRequestData;
import zidium.dto.getLogs.GetLogsResponse;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestRequestData;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestResponse;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeRequestData;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeResponse;
import zidium.dto.getRootComponent.GetRootComponentResponse;
import zidium.dto.sendLogs.LogMessageDto;
import zidium.dto.sendEvent.SendEventRequestData;
import zidium.dto.sendEvent.SendEventResponse;
import zidium.dto.sendLogs.SendLogsResponse;
import zidium.dto.sendMetric.SendMetricRequestData;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.sendUnitTestResult.SendUnitTestResultRequestData;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;
import zidium.dto.updateComponent.UpdateComponentRequestData;
import zidium.dto.updateComponent.UpdateComponentResponse;

public interface IZidiumTransport {
    
    public Token getToken();
    
    public void setToken(Token token);
    
    public FailedResponseInfo getLastFailedResponseInfo();
    
    public SendEventResponse sendEvent(SendEventRequestData data);
    
    public GetEventsResponse getEvents(GetEventsRequestData data);
    
    public GetEchoResponse getEcho(String message);
    
    public GetRootComponentResponse getRootComponent();
    
    public GetComponentByIdResponse getComponentById(String id);
    
    public GetOrAddComponentResponse getOrAddComponent(GetOrAddComponentRequestData data);
    
    public UpdateComponentResponse updateComponent(UpdateComponentRequestData data);
    
    public GetServerTimeResponse getServerTime();
    
    public GetLogConfigResponse getLogConfig(String componentId);
    
    public SendLogsResponse sendLogs(LogMessageDto[] logs);    
    
    public GetLogsResponse getLogs(GetLogsRequestData data);
    
    public SendUnitTestResultResponse sendUnitTestResult(SendUnitTestResultRequestData data);
    
    public GetOrCreateUnitTestResponse getOrCreateUnitTest(GetOrCreateUnitTestRequestData data);
    
    public GetOrCreateUnitTestTypeResponse getOrCreateUnitTestType(GetOrCreateUnitTestTypeRequestData data);
    
    public SendMetricResponse sendMetric(SendMetricRequestData data);
    
    public TrafficCounters getTrafficCounters();
}
