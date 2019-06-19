package zidium.unitTests;

import java.time.Duration;
import zidium.client.IZidiumClient;
import zidium.dto.sendUnitTestResult.SendUnitTestResultRequestData;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;
import zidium.webServices.IZidiumTransport;

public class UnitTestControl implements IUnitTestControl {

    private final String _id;
    private final IZidiumClient _client;
    
    public UnitTestControl(String id, IZidiumClient client){
        _id = id;
        _client = client;
    }
    
    @Override
    public boolean isFake() {
        return false;
    }
    
    protected SendUnitTestResultResponse SendResult(SendUnitTestResultRequestData data){
        IZidiumTransport transport = _client.getTransport();
        return transport.sendUnitTestResult(data);
    }

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult result) {
        SendUnitTestResultRequestData data = new SendUnitTestResultRequestData();
        data.UnitTestId = _id;
        data.Result = UnitTestResultHelper.toString(result);
        return SendResult(data);
    }

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult result, String message) {
        SendUnitTestResultRequestData data = new SendUnitTestResultRequestData();
        data.UnitTestId = _id;
        data.Result = UnitTestResultHelper.toString(result);
        data.Message = message;
        return SendResult(data);
    }    

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult result, String message, Duration actualTime) {
        SendUnitTestResultRequestData data = new SendUnitTestResultRequestData();
        data.UnitTestId = _id;
        data.Result = UnitTestResultHelper.toString(result);
        data.Message = message;
        data.ActualIntervalSeconds = (double)actualTime.getSeconds();
        return SendResult(data);
    }
}
