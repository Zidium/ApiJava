package zidium.unitTests;

import java.time.Duration;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;


public interface IUnitTestControl {
    
    public boolean isFake();
    
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus);

    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus, String message);
    
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus, String message, Duration actualTime);
}
