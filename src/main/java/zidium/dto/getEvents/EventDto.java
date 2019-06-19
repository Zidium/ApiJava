package zidium.dto.getEvents;

import java.util.Date;
import zidium.dto.ExtentionPropertyDto;

public class EventDto {
    
    public String Id;

    public String OwnerId;

    public String Importance;

    public int Count;

    public Date StartDate;

    public Date EndDate;

    public long JoinKeyHash;

    public String TypeId;

    public String TypeSystemName;

    public String TypeDisplayName;

    public String TypeCode;

    public String Message;

    public String Version;

    public String Category;

    public boolean IsUserHandled;

    public ExtentionPropertyDto[] Properties;
}
