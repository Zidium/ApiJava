package zidium.unitTestTypes;

import java.util.Date;
import zidium.client.IZidiumClient;

public class UnitTestTypeControlWrapper implements IUnitTestTypeControl {

    private IUnitTestTypeControl _control;
    private Date _lastGetTime = null;
    private final IZidiumClient _client;
    private final IUnitTestTypeIdProvider _provider;

    public UnitTestTypeControlWrapper(
            IZidiumClient client,
            IUnitTestTypeIdProvider provider) {
        _client = client;
        _provider = provider;
        _control = new FakeUnitTestTypeControl();
    }

    protected IUnitTestTypeControl getControl() {
        if (_control.isFake() && canLoad()) {
            _lastGetTime = new Date();
            String unitTestTypeId = _provider.getUnitTestTypeId();
            if (unitTestTypeId != null) {
                _control = new UnitTestTypeControl(unitTestTypeId, _client);
            }
        }
        return _control;
    }

    private boolean canLoad() {
        if (_lastGetTime == null) {
            return true;
        }
        long time = new Date().getTime() - _lastGetTime.getTime();
        return time > 1000 * 60; // прошло более минуты
    }

    @Override
    public boolean isFake() {
        IUnitTestTypeControl control = getControl();
        return control.isFake();
    }

    @Override
    public String getId() {
        IUnitTestTypeControl control = getControl();
        return control.getId();
    }

}
