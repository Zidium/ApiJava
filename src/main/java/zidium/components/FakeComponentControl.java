package zidium.components;

import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.dto.sendMetric.SendMetricRequestData;
import zidium.dto.sendMetric.SendMetricResponse;
import zidium.dto.updateComponent.UpdateComponentResponse;
import zidium.events.ZidiumEvent;
import zidium.logs.FakeLog;
import zidium.logs.ILog;
import zidium.unitTestTypes.IUnitTestTypeControl;
import zidium.unitTests.FakeUnitTestControl;
import zidium.unitTests.IUnitTestControl;

public class FakeComponentControl implements IComponentControl {

    private String _id;

    public FakeComponentControl() {

    }

    public FakeComponentControl(String id) {
        _id = id;
    }

    @Override
    public boolean isFake() {
        return true;
    }

    @Override
    public void addError(String message, Throwable exception) {
        // данный метод должен отрабатывать только wrapper
        // реальный компонент не должен его выполнять
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ILog getLog() {
        return FakeLog.Instance;
    }

    @Override
    public void addError(String message) {
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
        return new FakeComponentControl();
    }

    @Override
    public IComponentControl getOrCreateChild(GetOrAddComponentRequestData data) {
        return new FakeComponentControl();
    }

    @Override
    public IUnitTestControl getOrCreateUnitTest(String name, IUnitTestTypeControl type) {
        return new FakeUnitTestControl();
    }

    @Override
    public SendMetricResponse sendMetric(SendMetricRequestData data) {
        SendMetricResponse response = new SendMetricResponse();
        response.Code = 15;
        response.ErrorMessage = "fake control response";
        return response;
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
    public void addEvent(ZidiumEvent event) {
    }

    @Override
    public UpdateComponentResponse update(UpdateComponentData data) {
        UpdateComponentResponse response = new UpdateComponentResponse();
        response.Code = 15;
        response.ErrorMessage = "fake control response";
        return response;
    }
}
