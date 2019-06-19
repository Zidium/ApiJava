package zidium.dto.getLogs;

import java.util.Date;

public class GetLogsRequestData {
    public String ComponentId;
    public Date From;
    public Date To;
    public String[] Levels;
    public String Context;
    public Integer MaxCount;
}
