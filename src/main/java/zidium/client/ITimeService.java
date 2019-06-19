package zidium.client;

import java.util.Date;

public interface ITimeService {
    
    public Date getNow();
    
    public void syntchTime();
}
