package zidium.unitTestTypes;

import zidium.client.IZidiumClient;

public class UnitTestTypeControl implements IUnitTestTypeControl {

    private final String _id;
    private final IZidiumClient _client;
    
    public UnitTestTypeControl(String id, IZidiumClient client){
        _id = id;
        _client = client;
    }
    
    @Override
    public boolean isFake() {
        return false;
    }

    @Override
    public String getId() {
        return _id;
    }

}
