package zidium.dto.getLogConfig;

import java.util.Date;


public class GetLogConfigResponseData {
    public String ComponentId;
    public Date LastUpdateDate;
    public boolean Enabled;
    public boolean IsTraceEnabled;
    public boolean IsDebugEnabled;
    public boolean IsInfoEnabled;
    public boolean IsWarningEnabled;
    public boolean IsErrorEnabled;
    public boolean IsFatalEnabled;
}
