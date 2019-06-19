package zidium.client;

import zidium.webServices.IZidiumTransport;
import java.util.Date;
import zidium.helpers.DateHelper;
import zidium.dto.GetServerTimeResponse;


public class TimeService implements ITimeService {

    private final IZidiumTransport _transport;
    private Long _timeDiff;
    private Date _lastSynch = null;
    
    public TimeService(IZidiumTransport transport){
        _transport = transport;
    }
            
    private boolean canLoad(){
        if (_lastSynch==null){
            return true;
        }
        long time = new Date().getTime() - _lastSynch.getTime();
        return time> 1000 * 60; // прошла минута
    }
    
    private Long loadDiff(){
        try{
            GetServerTimeResponse response = _transport.getServerTime();
            if (response.success()){
                long diff = response.Data.getTime() - new Date().getTime();
                if (Math.abs(diff) < 30){
                    diff = 0;
                }
                return diff;
            }
        }        
        catch(Exception e){
            
        }
        return null;
    }
    
    private Long getTimeDiff(){
        if (_timeDiff==null){            
            if (canLoad()){
                _lastSynch = new Date();
                _timeDiff = loadDiff();
            }
        }        
        return _timeDiff;
    }
    
    @Override
    public Date getNow() {        
        Date now = new Date();
        Long diff = getTimeDiff();        
        if (diff==null){
            return now;
        }
        return DateHelper.addMilliseconds(now, diff);
    }    

    @Override
    public void syntchTime() {        
        _timeDiff = loadDiff();
        _lastSynch = new Date();
    }
}
