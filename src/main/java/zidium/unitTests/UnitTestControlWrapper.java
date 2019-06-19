package zidium.unitTests;

import java.time.Duration;
import java.util.Date;
import zidium.client.IZidiumClient;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;


public class UnitTestControlWrapper implements IUnitTestControl {

    private IUnitTestControl _control;
    private Date _lastGetTime = null;
    private final IZidiumClient _client;
    private final IUnitTestIdProvider _provider;
    
    public UnitTestControlWrapper(
        IZidiumClient client, 
        IUnitTestIdProvider provider){
        _client = client;
        _provider = provider;
        _control = new FakeUnitTestControl();
    }

    protected IUnitTestControl getControl(){
        if (_control.isFake() && canLoad()){
            _lastGetTime = new Date();
            String unitTestId = _provider.getUnitTestId();
            if (unitTestId!=null){                
                _control = new UnitTestControl(unitTestId, _client);               
            }            
        }
        return _control;
    }
    
    private boolean canLoad(){
        if (_lastGetTime==null){
            return true;
        }
        long time = new Date().getTime() - _lastGetTime.getTime();
        return time > 1000 * 60; // прошло более минуты
    }
    
    @Override
    public boolean isFake() {
        IUnitTestControl control = getControl();
        return control.isFake();
    }

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus) {
        IUnitTestControl control = getControl();
        return control.SendResult(resultStatus);
    }

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus, String message) {
        IUnitTestControl control = getControl();
        return control.SendResult(resultStatus, message);
    }    

    @Override
    public SendUnitTestResultResponse SendResult(UnitTestResult resultStatus, String message, Duration actualTime) {
        IUnitTestControl control = getControl();
        return control.SendResult(resultStatus, message, actualTime);
    }
}
