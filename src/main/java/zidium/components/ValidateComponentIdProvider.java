package zidium.components;

import zidium.client.IZidiumClient;
import zidium.webServices.IZidiumTransport;
import zidium.dto.getComponentById.GetComponentByIdResponse;


public class ValidateComponentIdProvider implements IComponentIdProvider {
    
    private final IZidiumClient _client;
    private final String _id;
    
    public ValidateComponentIdProvider(IZidiumClient client, String id){
        _client = client;
        _id = id;
    }    

    @Override
    public String getComponentId() {
        try{
            IZidiumTransport transport = _client.getTransport();
            GetComponentByIdResponse response = transport.getComponentById(_id);
            if (response.success() && response.Data!=null){
                return response.Data.Id;
            }
        }
        catch(Exception e){
            
        }
        return null;
    }
}
