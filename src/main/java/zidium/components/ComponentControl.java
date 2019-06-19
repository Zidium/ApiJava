package zidium.components;

import zidium.logs.ILog;
import zidium.client.IZidiumClient;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.sendMetric.SendMetricRequestData;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.updateComponent.UpdateComponentRequestData;
import zidium.dto.updateComponent.UpdateComponentResponse;
import zidium.events.ZidiumEvent;
import zidium.unitTestTypes.IUnitTestTypeControl;
import zidium.unitTests.IUnitTestControl;
import zidium.webServices.IZidiumTransport;

public class ComponentControl implements IComponentControl {

    private final String _id;
    private final IZidiumClient _client;

    public ComponentControl(IZidiumClient client, String id) {
        _id = id;
        _client = client;
    }

    @Override
    public boolean isFake() {
        return false;
    }

    @Override
    public void addEvent(ZidiumEvent event) {
        _client.getEventManager().add(event);
    }

    @Override
    public void addError(String message) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addError(String message, Throwable exception) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ZidiumEvent createError(String message) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ZidiumEvent createError(String message, Throwable exception) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ILog getLog() {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getId() {
        return _id;
    }

    @Override
    public IComponentControl getOrCreateChild(String name) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IComponentControl getOrCreateChild(GetOrAddComponentRequestData data) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IUnitTestControl getOrCreateUnitTest(String name, IUnitTestTypeControl type) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SendMetricResponse sendMetric(SendMetricRequestData data) {
        if (data != null) {
            data.ComponentId = _id;
        }
        IZidiumTransport transport = _client.getTransport();
        return transport.sendMetric(data);
    }

    @Override
    public UpdateComponentResponse update(UpdateComponentData data) {
        UpdateComponentRequestData requestData = new UpdateComponentRequestData();
        requestData.Id = getId();
        requestData.ParentId = data.ParentId;
        requestData.SystemName = data.SystemName;
        requestData.DisplayName = data.DisplayName;
        requestData.TypeId = data.TypeId;
        requestData.Version = data.Version;
        requestData.Properties = data.Properties;
        
        IZidiumTransport transport = _client.getTransport();
        return transport.updateComponent(requestData);
    }
}
