package zidium.components;

import java.util.Date;
import zidium.client.IZidiumClient;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.sendMetric.SendMetricRequestData;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.updateComponent.UpdateComponentResponse;
import zidium.events.IExceptionToEventConverter;
import zidium.events.ZidiumEvent;
import zidium.logs.ILog;
import zidium.logs.LogWrapper;
import zidium.unitTestTypes.IUnitTestTypeControl;
import zidium.unitTests.GetOrCreateUnitTestIdProvider;
import zidium.unitTests.IUnitTestControl;
import zidium.unitTests.IUnitTestIdProvider;
import zidium.unitTests.UnitTestControlWrapper;

public class ComponentControlWrapper implements IComponentControl {

    private final IZidiumClient _client;
    private final IComponentIdProvider _provider;
    private final LogWrapper _logWrapper;

    private IComponentControl _control;
    private Date _lastGetTime = null;

    public ComponentControlWrapper(
            IZidiumClient client,
            IComponentIdProvider provider,
            String componentId) {
        _provider = provider;
        _client = client;
        _control = new FakeComponentControl(componentId);
        _logWrapper = new LogWrapper(this, client);
    }

    private boolean canLoad() {
        if (_lastGetTime == null) {
            return true;
        }
        long time = new Date().getTime() - _lastGetTime.getTime();
        return time > 1000 * 60; // прошло более минуты
    }

    private IComponentControl getComponentControl() {
        if (_control.isFake() && canLoad()) {
            _lastGetTime = new Date();
            String componentId = _provider.getComponentId();
            if (componentId != null) {
                _control = new ComponentControl(_client, componentId);
            }
        }
        return _control;
    }

    @Override
    public ILog getLog() {
        return _logWrapper;
    }

    @Override
    public IComponentControl getOrCreateChild(String name) {
        GetOrAddComponentRequestData data = new GetOrAddComponentRequestData();
        data.DisplayName = name;
        data.SystemName = name;
        data.TypeId = ComponentTypeId.Others;
        GetOrCreateChildComponentIdProvider provider = new GetOrCreateChildComponentIdProvider(_client, data, this);
        return _client.getComponentControl(provider);
    }

    @Override
    public IComponentControl getOrCreateChild(GetOrAddComponentRequestData data) {
        IComponentIdProvider provider = new GetOrCreateChildComponentIdProvider(_client, data);
        return _client.getComponentControl(provider);
    }

    @Override
    public String getId() {
        IComponentControl control = getComponentControl();
        return control.getId();
    }

    @Override
    public boolean isFake() {
        IComponentControl control = getComponentControl();
        return control.isFake();
    }

    @Override
    public void addEvent(ZidiumEvent event) {
        IComponentControl control = getComponentControl();
        control.addEvent(event);
    }

    @Override
    public void addError(String message) {
        addEvent(createError(message));
    }

    @Override
    public void addError(String message, Throwable exception) {
        addEvent(createError(message, exception));
    }

    @Override
    public ZidiumEvent createError(String message) {
        IExceptionToEventConverter converter = _client.getExceptionToEventConverter();
        ZidiumEvent event = converter.getEvent(message);
        event.setComponentControl(this);
        return event;
    }

    @Override
    public ZidiumEvent createError(String message, Throwable exception) {
        IExceptionToEventConverter converter = _client.getExceptionToEventConverter();
        ZidiumEvent event = converter.getEvent(message, exception);
        event.setComponentControl(this);
        return event;
    }

    @Override
    public IUnitTestControl getOrCreateUnitTest(String name, IUnitTestTypeControl type) {
        IUnitTestIdProvider provider = new GetOrCreateUnitTestIdProvider(_client, this, name, type);
        return new UnitTestControlWrapper(_client, provider);
    }

    @Override
    public SendMetricResponse sendMetric(SendMetricRequestData data) {
        IComponentControl control = getComponentControl();
        return control.sendMetric(data);
    }

    @Override
    public UpdateComponentResponse update(UpdateComponentData data) {
        IComponentControl control = getComponentControl();
        return control.update(data);
    }

}
