package zidium.logs;

import java.util.Date;
import zidium.dto.ExtentionPropertyCollection;
import zidium.components.IComponentControl;


public class LogMessage {
    
    public IComponentControl ComponentControl;
    public String Message;
    public String Level;
    public long Order;
    public Date Time;
    
    private ExtentionPropertyCollection _properties = new ExtentionPropertyCollection();
    
    public ExtentionPropertyCollection getProperties(){
        return _properties;
    }
}
