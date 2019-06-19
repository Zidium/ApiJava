package zidium.events;


public interface IEventManager {
    public void add(ZidiumEvent event);
    public void flush();
    public void setMaxEventsCount(int count);
    public int getMaxEventsCount();
}
