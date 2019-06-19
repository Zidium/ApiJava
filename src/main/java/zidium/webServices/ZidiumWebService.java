package zidium.webServices;

import zidium.dto.GetServerTimeResponse;
import zidium.dto.Response;
import zidium.dto.Request;
import zidium.dto.sendLogs.SendLogsRequest;
import zidium.dto.sendLogs.SendLogsResponse;
import zidium.dto.sendEvent.SendEventRequest;
import zidium.dto.sendEvent.SendEventResponse;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequest;
import zidium.dto.getOrAddComponent.GetOrAddComponentResponse;
import zidium.dto.getLogConfig.GetLogConfigResponse;
import zidium.dto.getLogConfig.GetLogConfigRequest;
import zidium.dto.getEcho.GetEchoResponse;
import zidium.dto.getEcho.GetEchoRequest;
import java.lang.reflect.Field;
import java.util.Date;
import zidium.http.ZHttpClient;
import zidium.http.ZHttpRequest;
import zidium.http.ZHttpResponse;
import zidium.dto.getComponentById.GetComponentByIdRequest;
import zidium.dto.getComponentById.GetComponentByIdResponse;
import zidium.dto.getEvents.GetEventsRequest;
import zidium.dto.getEvents.GetEventsResponse;
import zidium.dto.getLogs.GetLogsRequest;
import zidium.dto.getLogs.GetLogsResponse;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestRequest;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestResponse;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeRequest;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeResponse;
import zidium.dto.getRootComponent.GetRootComponentRequest;
import zidium.dto.getRootComponent.GetRootComponentResponse;
import zidium.dto.sendMetric.SendMetricRequest;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.sendUnitTestResult.SendUnitTestResultRequest;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;
import zidium.dto.updateComponent.UpdateComponentRequest;
import zidium.dto.updateComponent.UpdateComponentResponse;

public class ZidiumWebService implements IZidiumWebService {

    private String _url;
    private ISerializer _serializer = null;
    private FailedResponseInfo _lastFailedResponse;
    private final TrafficCounters _trafficCounters = new TrafficCounters();

    public ZidiumWebService(String url) {
        _url = url;
        if (_url.endsWith("/") == false) {
            _url += "/";
        }
        _serializer = new GsonSerializer();
    }

    @Override
    public FailedResponseInfo getLastFailedResponseInfo() {
        return _lastFailedResponse;
    }

    private void executeRequest(String method, Request request, Response response) {
        Date requestTime = new Date();
        try {
            String url = _url + method;
            ZHttpClient httpClient = new ZHttpClient();
            ZHttpRequest httpRequest = new ZHttpRequest();
            httpRequest.Url = url;
            httpRequest.Method = "POST";
            httpRequest.addProperty("Content-Type", "application/json");
            if (request != null) {
                String json = _serializer.toString(request);
                byte[] requestBytes = json.getBytes("UTF-8");
                httpRequest.Data = requestBytes;
            }
            _trafficCounters.addUpload(httpRequest.Data.length);

            ZHttpResponse httpResponse = httpClient.ExecuteRequest(httpRequest);
            _trafficCounters.addDownload(httpResponse.Bytes.length);

            httpResponse.validateCode();
            byte[] responseBytes = httpResponse.Bytes;
            String json = new String(responseBytes, "UTF-8");
            Class<?> responseClass = response.getClass();
            Response response2 = (Response) _serializer.fromString(json, responseClass);
            if (response2 == null) {
                throw new Exception("response is null");
            }

            // копируем даные
            response.Code = response2.Code;
            response.ErrorMessage = response2.ErrorMessage;
            if (response2.success()) {
                Field dataField = responseClass.getField("Data");
                Object data = dataField.get(response2);
                if (data != null) {
                    dataField.set(response, data);
                }
            }
        } catch (Exception exception) {
            response.Code = 1;
            response.ErrorMessage = "web service error: " + exception.getMessage();

            // запомним последний запрос с ошибкой (помогает в отладке)
            FailedResponseInfo failedResponse = new FailedResponseInfo();
            failedResponse.Request = request;
            failedResponse.Response = response;
            failedResponse.RequestTime = requestTime;
            failedResponse.ResponseTime = new Date();
            _lastFailedResponse = failedResponse;
        }
    }

    @Override
    public SendEventResponse sendEvent(SendEventRequest request) {
        SendEventResponse response = new SendEventResponse();
        executeRequest("SendEvent", request, response);
        return response;
    }

    @Override
    public GetEchoResponse getEcho(GetEchoRequest request) {
        GetEchoResponse response = new GetEchoResponse();
        executeRequest("GetEcho", request, response);
        return response;
    }

    @Override
    public GetOrAddComponentResponse getOrAddComponent(GetOrAddComponentRequest request) {
        GetOrAddComponentResponse response = new GetOrAddComponentResponse();
        executeRequest("GetOrAddComponent", request, response);
        return response;
    }
    
    
    @Override
    public UpdateComponentResponse updateComponent(UpdateComponentRequest request) {
        UpdateComponentResponse response = new UpdateComponentResponse();
        executeRequest("UpdateComponent", request, response);
        return response;
    }

    @Override
    public GetServerTimeResponse getServerTime() {
        GetServerTimeResponse response = new GetServerTimeResponse();
        executeRequest("GetServerTime", null, response);
        return response;
    }

    @Override
    public GetLogConfigResponse getLogConfig(GetLogConfigRequest request) {
        GetLogConfigResponse response = new GetLogConfigResponse();
        executeRequest("GetLogConfig", request, response);
        return response;
    }

    @Override
    public SendLogsResponse sendLogs(SendLogsRequest request) {
        SendLogsResponse response = new SendLogsResponse();
        executeRequest("SendLogs", request, response);
        return response;
    }

    @Override
    public GetComponentByIdResponse getComponentById(GetComponentByIdRequest request) {
        GetComponentByIdResponse response = new GetComponentByIdResponse();
        executeRequest("GetComponentById", request, response);
        return response;
    }

    @Override
    public GetRootComponentResponse getRootComponent(GetRootComponentRequest request) {
        GetRootComponentResponse response = new GetRootComponentResponse();
        executeRequest("GetRootComponent", request, response);
        return response;
    }

    @Override
    public GetEventsResponse getEvents(GetEventsRequest request) {
        GetEventsResponse response = new GetEventsResponse();
        executeRequest("GetEvents", request, response);
        return response;
    }

    @Override
    public GetLogsResponse getLogs(GetLogsRequest request) {
        GetLogsResponse response = new GetLogsResponse();
        executeRequest("GetLogs", request, response);
        return response;
    }

    @Override
    public SendUnitTestResultResponse sendUnitTestResult(SendUnitTestResultRequest request) {
        SendUnitTestResultResponse response = new SendUnitTestResultResponse();
        executeRequest("SendUnitTestResult", request, response);
        return response;
    }

    @Override
    public GetOrCreateUnitTestResponse getOrCreateUnitTest(GetOrCreateUnitTestRequest request) {
        GetOrCreateUnitTestResponse response = new GetOrCreateUnitTestResponse();
        executeRequest("GetOrCreateUnitTest", request, response);
        return response;
    }

    @Override
    public SendMetricResponse sendMetric(SendMetricRequest request) {
        SendMetricResponse response = new SendMetricResponse();
        executeRequest("SendMetric", request, response);
        return response;
    }

    @Override
    public GetOrCreateUnitTestTypeResponse getOrCreateUnitTestType(GetOrCreateUnitTestTypeRequest request) {
        GetOrCreateUnitTestTypeResponse response = new GetOrCreateUnitTestTypeResponse();
        executeRequest("GetOrCreateUnitTestType", request, response);
        return response;
    }

    @Override
    public TrafficCounters getTrafficCounters() {
        return _trafficCounters;
    }

}
