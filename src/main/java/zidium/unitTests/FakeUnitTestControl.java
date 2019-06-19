package zidium.unitTests;

import java.time.Duration;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;


public class FakeUnitTestControl implements IUnitTestControl {

    @Override
    public boolean isFake() {
        return true;
    }
    
    private SendUnitTestResultResponse getResponse(){
        SendUnitTestResultResponse response = new SendUnitTestResultResponse();
        response.Code = 15;
        response.ErrorMessage = "fake control response";
        return response;
    }

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus) {
        return getResponse();
    }

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus, String message) {
        return getResponse();
    }    

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus, String message, Duration actualTime) {
        return getResponse();
    }
}
