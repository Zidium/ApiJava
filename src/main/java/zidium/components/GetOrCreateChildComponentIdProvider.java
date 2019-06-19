package zidium.components;

import zidium.client.IZidiumClient;
import zidium.webServices.IZidiumTransport;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.getOrAddComponent.GetOrAddComponentResponse;


public class GetOrCreateChildComponentIdProvider implements IComponentIdProvider {
    
    private final GetOrAddComponentRequestData _data;
    private final IZidiumClient _client;
    private final IComponentControl _parent;
    
    public GetOrCreateChildComponentIdProvider(
            IZidiumClient client, 
            GetOrAddComponentRequestData data){
        _data = data;
        _client = client;
        _parent = null;
    }
    
    public GetOrCreateChildComponentIdProvider(
            IZidiumClient client, 
            GetOrAddComponentRequestData data,
            IComponentControl parent){
        _data = data;
        _client = client;
        _parent = parent;
    }

    @Override
    public String getComponentId() {
        try{
            if (_parent!=null){
                if (_parent.isFake()){
                    return null;
                }
                _data.ParentComponentId = _parent.getId();
            }
            IZidiumTransport transport = _client.getTransport();
            GetOrAddComponentResponse ressponse = transport.getOrAddComponent(_data);
            if (ressponse.success() && ressponse.Data!=null){
                return ressponse.Data.Id;
            }
        }
        catch(Exception e){
            
        }
        return null;
    }
}
