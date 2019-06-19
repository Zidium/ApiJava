package zidium.events;


public interface IExceptionToEventConverter {
    public ZidiumEvent getEvent(String message);
    public ZidiumEvent getEvent(Throwable exception);
    public ZidiumEvent getEvent(String message, Throwable exception);
}
