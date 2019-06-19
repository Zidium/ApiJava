package zidium.client;

import zidium.helpers.GuidHelper;
import zidium.components.IComponentControl;
import zidium.dto.ExtentionPropertyDto;


public class ZidiumTestHelper {
    
    public static IZidiumClient getTestClient(){
        return ZidiumClient.loadFromConfig("target\\test\\zidium.properties");
    }  
    
    public static String getContainerComponentId(IZidiumClient client){
        IComponentControl root = client.getRootComponentControl();
        return root.getOrCreateChild("JavaTestsContainer").getId();
    }
    
    public static IComponentControl getRandomComponent(IZidiumClient client){
        String componentId = getContainerComponentId(client);
        IComponentControl container = client.getComponentControl(componentId);
        String guid = GuidHelper.getRandom();
        return container.getOrCreateChild("test-" + guid);
    }
    
    public static IComponentControl getContainerComponentControl(){
        IZidiumClient client = getTestClient();
        String componentId = getContainerComponentId(client);
        return client.getComponentControl(componentId);
    }
    
    public static ExtentionPropertyDto getStackProperty(ExtentionPropertyDto[] properties){
        if (properties==null){
            return null;
        }
        for (ExtentionPropertyDto property : properties) {
            if ("Stack".equals(property.Name)){
                return property;
            }
        }
        return null;
    }
}
