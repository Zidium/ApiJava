package zidium.dto.updateComponent;

import zidium.dto.ExtentionPropertyDto;

public class UpdateComponentRequestData {
    public String Id;
    public String ParentId;
    public String SystemName;
    public String DisplayName;
    public String TypeId;
    public String Version;
    public ExtentionPropertyDto[] Properties;
}
