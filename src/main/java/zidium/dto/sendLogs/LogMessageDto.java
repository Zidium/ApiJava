package zidium.dto.sendLogs;

import java.util.Date;
import zidium.dto.ExtentionPropertyDto;


public class LogMessageDto {
    public String ComponentId;
    public Date Date;
    public long Order;
    public String Level;
    public String Message;
    public ExtentionPropertyDto[] Properties;
}
