package zidium.dto.getLogs;

import java.util.Date;
import zidium.dto.ExtentionPropertyDto;

public class LogMessageDto {
    public String Id;
    public String Level;
    public Date Date;
    public int Order;
    public String Message;
    public String Context;
    public ExtentionPropertyDto[] Properties;
}
