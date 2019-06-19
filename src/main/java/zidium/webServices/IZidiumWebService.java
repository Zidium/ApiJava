package zidium.webServices;

import zidium.dto.sendLogs.SendLogsRequest;
import zidium.dto.sendLogs.SendLogsResponse;
import zidium.dto.sendEvent.SendEventResponse;
import zidium.dto.sendEvent.SendEventRequest;
import zidium.dto.getRootComponent.GetRootComponentRequest;
import zidium.dto.getRootComponent.GetRootComponentResponse;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequest;
import zidium.dto.getOrAddComponent.GetOrAddComponentResponse;
import zidium.dto.getLogConfig.GetLogConfigResponse;
import zidium.dto.getLogConfig.GetLogConfigRequest;
import zidium.dto.getEvents.GetEventsRequest;
import zidium.dto.getEvents.GetEventsResponse;
import zidium.dto.getEcho.GetEchoRequest;
import zidium.dto.getEcho.GetEchoResponse;
import zidium.dto.getComponentById.GetComponentByIdRequest;
import zidium.dto.getComponentById.GetComponentByIdResponse;
import zidium.dto.GetServerTimeResponse;
import zidium.dto.getLogs.GetLogsRequest;
import zidium.dto.getLogs.GetLogsResponse;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestRequest;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestResponse;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeRequest;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeResponse;
import zidium.dto.sendMetric.SendMetricRequest;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.sendUnitTestResult.SendUnitTestResultRequest;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;
import zidium.dto.updateComponent.UpdateComponentRequest;
import zidium.dto.updateComponent.UpdateComponentResponse;

/**
 * Веб-сервис АПИ для системы мониторинга Zidium
 */
public interface IZidiumWebService {

    public FailedResponseInfo getLastFailedResponseInfo();

    public SendEventResponse sendEvent(SendEventRequest request);

    public GetEventsResponse getEvents(GetEventsRequest request);

    public GetEchoResponse getEcho(GetEchoRequest request);

    public GetRootComponentResponse getRootComponent(GetRootComponentRequest request);

    public GetComponentByIdResponse getComponentById(GetComponentByIdRequest request);

    public GetOrAddComponentResponse getOrAddComponent(GetOrAddComponentRequest request);
    
    public UpdateComponentResponse updateComponent(UpdateComponentRequest request);

    public GetServerTimeResponse getServerTime();

    public GetLogConfigResponse getLogConfig(GetLogConfigRequest request);

    public SendLogsResponse sendLogs(SendLogsRequest request);

    public GetLogsResponse getLogs(GetLogsRequest request);

    public SendUnitTestResultResponse sendUnitTestResult(SendUnitTestResultRequest request);

    public GetOrCreateUnitTestResponse getOrCreateUnitTest(GetOrCreateUnitTestRequest request);

    public GetOrCreateUnitTestTypeResponse getOrCreateUnitTestType(GetOrCreateUnitTestTypeRequest request);

    public SendMetricResponse sendMetric(SendMetricRequest request);
    
    public TrafficCounters getTrafficCounters();
}
