package zidium.unitTests;

import zidium.client.IZidiumClient;
import zidium.components.IComponentControl;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestRequestData;
import zidium.dto.getOrCreateUnitTest.GetOrCreateUnitTestResponse;
import zidium.unitTestTypes.IUnitTestTypeControl;
import zidium.webServices.IZidiumTransport;

public class GetOrCreateUnitTestIdProvider implements IUnitTestIdProvider {

    private final String _name;
    private final IUnitTestTypeControl _type;
    private final IZidiumClient _client;
    private final IComponentControl _component;
    public String UnitTestDisplayName;

    public GetOrCreateUnitTestIdProvider(
            IZidiumClient client,
            IComponentControl componentControl,
            String name,
            IUnitTestTypeControl type) {
        _client = client;
        _component = componentControl;
        _name = name;
        _type = type;
    }

    @Override
    public String getUnitTestId() {
        if (_component.isFake()) {
            return null;
        }
        if (_type.isFake()) {
            return null;
        }
        IZidiumTransport transport = _client.getTransport();
        GetOrCreateUnitTestRequestData data = new GetOrCreateUnitTestRequestData();
        data.ComponentId = _component.getId();
        data.DisplayName = UnitTestDisplayName;
        data.SystemName = _name;
        data.UnitTestTypeId = _type.getId();
        GetOrCreateUnitTestResponse response = transport.getOrCreateUnitTest(data);
        if (response.success() && response.Data != null) {
            return response.Data.Id;
        }
        return null;
    }
}
