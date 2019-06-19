package zidium.logs;

import zidium.components.IComponentControl;


public interface ILogManager {
    public LogQueue createLogQueue(IComponentControl componentControl);
    public void beginProcessQueues();
    public void flush();
    public void setMaxEventsCount(int count);
    public int getMaxEventsCount();
}
