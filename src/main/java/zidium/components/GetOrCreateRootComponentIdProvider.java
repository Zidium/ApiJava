package zidium.components;

import zidium.client.IZidiumClient;
import zidium.webServices.IZidiumTransport;
import zidium.dto.getRootComponent.GetRootComponentResponse;


public class GetOrCreateRootComponentIdProvider implements IComponentIdProvider {

    private final IZidiumClient _client;
    
    public GetOrCreateRootComponentIdProvider(IZidiumClient client){
        _client = client;
    }
    
    @Override
    public String getComponentId() {
        try{
            IZidiumTransport transport = _client.getTransport();
            GetRootComponentResponse response = transport.getRootComponent();
            if (response.success() && response.Data!=null){
                return response.Data.Id;
            }
        }
        catch(Exception e){
            
        }
        return null;
    }    
}
