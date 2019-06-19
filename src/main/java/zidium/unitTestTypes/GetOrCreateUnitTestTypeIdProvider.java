package zidium.unitTestTypes;

import zidium.client.IZidiumClient;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeRequestData;
import zidium.dto.getOrCreateUnitTestType.GetOrCreateUnitTestTypeResponse;
import zidium.webServices.IZidiumTransport;

public class GetOrCreateUnitTestTypeIdProvider implements IUnitTestTypeIdProvider {

    private final String _name;
    private final IZidiumClient _client;
    public String UnitTestTypeDisplayName;

    public GetOrCreateUnitTestTypeIdProvider(
            IZidiumClient client,
            String name) {
        _client = client;
        _name = name;
    }

    @Override
    public String getUnitTestTypeId() {
        IZidiumTransport transport = _client.getTransport();
        GetOrCreateUnitTestTypeRequestData data = new GetOrCreateUnitTestTypeRequestData();
        data.DisplayName = UnitTestTypeDisplayName;
        data.SystemName = _name;
        GetOrCreateUnitTestTypeResponse response = transport.getOrCreateUnitTestType(data);
        if (response.success() && response.Data != null) {
            return response.Data.Id;
        }
        return null;
    }
}
