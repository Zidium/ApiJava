package zidium.dto;

import java.util.Date;

public class ComponentDto {
    public String Id;
    public String ParentId;
    public ComponentTypeDto Type;
    public String SystemName;
    public String DisplayName;
    public Date CreatedDate;
    public String Version;
    public ExtentionPropertyDto[] Properties;
}
