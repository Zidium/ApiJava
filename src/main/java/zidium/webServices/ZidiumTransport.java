package zidium.webServices;

import zidium.dto.getEcho.GetEchoRequest;
import zidium.dto.getEcho.GetEchoRequestData;
import zidium.dto.getEcho.GetEchoResponse;
import zidium.dto.getLogConfig.GetLogConfigRequest;
import zidium.dto.getLogConfig.GetLogConfigRequestData;
import zidium.dto.getLogConfig.GetLogConfigResponse;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequest;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.getOrAddComponent.GetOrAddComponentResponse;
import zidium.dto.GetServerTimeResponse;
import zidium.dto.sendLogs.LogMessageDto;
import zidium.dto.Request;
import zidium.dto.sendEvent.SendEventRequest;
import zidium.dto.sendEvent.SendEventRequestData;
import zidium.dto.sendEvent.SendEventResponse;
import zidium.dto.sendLogs.SendLogsRequest;
import zidium.dto.sendLogs.SendLogsResponse;
import zidium.dto.Token;
import zidium.dto.getComponentById.GetComponentByIdRequest;
import zidium.dto.getComponentById.GetComponentByIdRequestData;
import zidium.dto.getComponentById.GetComponentByIdResponse;
import zidium.dto.getEvents.GetEventsRequest;
import zidium.dto.getEvents.GetEventsRequestData;
import zidium.dto.getEvents.GetEventsResponse;
import zidium.dto.getLogs.GetLogsRequest;
import zidium.dto.getLogs.GetLogsRequestData;
import zidium.dto.getLogs.GetLogsResponse;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestRequest;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestRequestData;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestResponse;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeRequest;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeRequestData;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeResponse;
import zidium.dto.getRootComponent.GetRootComponentRequest;
import zidium.dto.getRootComponent.GetRootComponentResponse;
import zidium.dto.sendMetric.SendMetricRequest;
import zidium.dto.sendMetric.SendMetricRequestData;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.sendUnitTestResult.SendUnitTestResultRequest;
import zidium.dto.sendUnitTestResult.SendUnitTestResultRequestData;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;
import zidium.dto.updateComponent.UpdateComponentRequest;
import zidium.dto.updateComponent.UpdateComponentRequestData;
import zidium.dto.updateComponent.UpdateComponentResponse;

public class ZidiumTransport implements IZidiumTransport {

    private Token _token;
    private final IZidiumWebService _webService;

    public ZidiumTransport(Token token, IZidiumWebService webService) {
        _token = token;
        _webService = webService;
    }

    private void initRequest(Request request) {
        request.Token = _token;
    }

    @Override
    public FailedResponseInfo getLastFailedResponseInfo() {
        return _webService.getLastFailedResponseInfo();
    }

    @Override
    public SendEventResponse sendEvent(SendEventRequestData data) {
        SendEventRequest request = new SendEventRequest();
        request.Data = data;
        initRequest(request);
        return _webService.sendEvent(request);
    }

    @Override
    public GetEchoResponse getEcho(String message) {
        GetEchoRequest request = new GetEchoRequest();
        request.Data = new GetEchoRequestData();
        request.Data.Message = message;
        initRequest(request);
        return _webService.getEcho(request);
    }

    @Override
    public GetOrAddComponentResponse getOrAddComponent(GetOrAddComponentRequestData data) {
        GetOrAddComponentRequest request = new GetOrAddComponentRequest();
        request.Data = data;
        initRequest(request);
        return _webService.getOrAddComponent(request);
    }

    @Override
    public UpdateComponentResponse updateComponent(UpdateComponentRequestData data) {
        UpdateComponentRequest request = new UpdateComponentRequest();
        request.Data = data;
        initRequest(request);
        return _webService.updateComponent(request);
    }

    @Override
    public GetServerTimeResponse getServerTime() {
        return _webService.getServerTime();
    }

    @Override
    public GetLogConfigResponse getLogConfig(String componentId) {
        GetLogConfigRequest request = new GetLogConfigRequest();
        request.Data = new GetLogConfigRequestData();
        request.Data.ComponentId = componentId;
        initRequest(request);
        return _webService.getLogConfig(request);
    }

    @Override
    public SendLogsResponse sendLogs(LogMessageDto[] data) {
        SendLogsRequest request = new SendLogsRequest();
        request.Data = data;
        initRequest(request);
        return _webService.sendLogs(request);
    }

    @Override
    public GetComponentByIdResponse getComponentById(String id) {
        GetComponentByIdRequest request = new GetComponentByIdRequest();
        request.Data = new GetComponentByIdRequestData();
        request.Data.ComponentId = id;
        initRequest(request);
        return _webService.getComponentById(request);
    }

    @Override
    public GetRootComponentResponse getRootComponent() {
        GetRootComponentRequest request = new GetRootComponentRequest();
        request.Data = null; // нет данных на вход
        initRequest(request);
        return _webService.getRootComponent(request);
    }

    @Override
    public GetEventsResponse getEvents(GetEventsRequestData data) {
        GetEventsRequest request = new GetEventsRequest();
        request.Data = data;
        initRequest(request);
        return _webService.getEvents(request);
    }

    @Override
    public GetLogsResponse getLogs(GetLogsRequestData data) {
        GetLogsRequest request = new GetLogsRequest();
        request.Data = data;
        initRequest(request);
        return _webService.getLogs(request);
    }

    @Override
    public Token getToken() {
        return _token;
    }

    @Override
    public void setToken(Token token) {
        _token = token;
    }

    @Override
    public SendUnitTestResultResponse sendUnitTestResult(SendUnitTestResultRequestData data) {
        SendUnitTestResultRequest request = new SendUnitTestResultRequest();
        request.Data = data;
        initRequest(request);
        return _webService.sendUnitTestResult(request);
    }

    @Override
    public GetOrCreateUnitTestResponse getOrCreateUnitTest(GetOrCreateUnitTestRequestData data) {
        GetOrCreateUnitTestRequest request = new GetOrCreateUnitTestRequest();
        request.Data = data;
        initRequest(request);
        return _webService.getOrCreateUnitTest(request);
    }

    @Override
    public SendMetricResponse sendMetric(SendMetricRequestData data) {
        SendMetricRequest request = new SendMetricRequest();
        request.Data = data;
        initRequest(request);
        return _webService.sendMetric(request);
    }

    @Override
    public GetOrCreateUnitTestTypeResponse getOrCreateUnitTestType(GetOrCreateUnitTestTypeRequestData data) {
        GetOrCreateUnitTestTypeRequest request = new GetOrCreateUnitTestTypeRequest();
        request.Data = data;
        initRequest(request);
        return _webService.getOrCreateUnitTestType(request);
    }

    @Override
    public TrafficCounters getTrafficCounters() {
        return _webService.getTrafficCounters();
    }

}
