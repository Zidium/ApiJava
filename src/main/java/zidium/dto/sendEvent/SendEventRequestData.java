package zidium.dto.sendEvent;

import java.util.Date;
import zidium.dto.ExtentionPropertyDto;
import zidium.dto.ExtentionPropertyDto;


public class SendEventRequestData {
    public String ComponentId;
    public String TypeSystemName;
    public String TypeDisplayName;
    public String TypeCode;
    public String Category;
    public String Message;
    public Date StartDate;
    public int Count;
    public String Importance;    
    public ExtentionPropertyDto[] Properties;
}
