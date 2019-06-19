package zidium.events;

import zidium.helpers.ExceptionHelper;
import zidium.client.IZidiumClient;
import zidium.dto.ExtentionPropertyCollection;


public class ExceptionToEventConverter implements IExceptionToEventConverter {

    private final IZidiumClient _client;
    
    public ExceptionToEventConverter(IZidiumClient client) {
        _client = client;
    }
   
    private String getSimpleClassName(String className){
        String[] units = className.split("\\.");
        if (units.length > 0){
            return units[units.length - 1];
        } 
        return className;
    }
    
    private void setType(
            ZidiumEvent event, 
            Throwable exception, 
            StackTraceElement stackTraceElement){
        
        // нет стека
        if (stackTraceElement==null){
            String type = exception.getClass().getCanonicalName();
            event.setTypeSystemName(type);
            return;
        }
        
        // есть стек
        ExtentionPropertyCollection properties = event.getPropertis();
        String stack = ExceptionHelper.getStackTraceAsString(exception); 
        properties.addString("Stack", stack);
        properties.addInteger("LineNumber", stackTraceElement.getLineNumber());
        properties.addString("FileName", stackTraceElement.getFileName());
        properties.addString("MethodName", stackTraceElement.getMethodName());
             
        // systemName
        String systemType = exception.getClass().getName()
                + " in " 
                + stackTraceElement.getClassName() 
                + "."
                + stackTraceElement.getMethodName();
        event.setTypeSystemName(systemType);
        
        // displayName
        String simpleClassName = getSimpleClassName(stackTraceElement.getClassName());
        String displayName = exception.getClass().getSimpleName()
                + " in " 
                + simpleClassName
                + "."
                + stackTraceElement.getMethodName();
        event.setTypeDisplayName(displayName);
    }
    
    private void setType(ZidiumEvent event, Throwable exception){               
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        if (stackTraceElements!=null){
            // есть стек            
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if (stackTraceElement.isNativeMethod()==false){
                    String className = stackTraceElement.getClassName(); 
                    if (className.startsWith("java.")){
                        continue;
                    }
                    setType(event, exception, stackTraceElement);
                    return;
                }
            }
            // не нашли нормальный фрейм, возьмем первый
            if (stackTraceElements.length > 0){
                setType(event, exception, stackTraceElements[0]);
                return;
            }            
        }        
        // нет стека
        setType(event, exception, null);                
    }
    
    @Override
    public ZidiumEvent getEvent(String message) {
        ZidiumEvent event = new ZidiumEvent();
        event.setMessage(message);
        event.setTypeSystemName("unknown type");
        event.setImportance(EventImportance.ALARM);
        event.setDate(_client.getTimeService().getNow());
        return event;
    }

    @Override
    public ZidiumEvent getEvent(Throwable exception) {
        ZidiumEvent event = new ZidiumEvent();
        event.setMessage(exception.getMessage());
        event.setImportance(EventImportance.ALARM);
        setType(event, exception);
        event.setDate(_client.getTimeService().getNow());
        return event;
    }

    @Override
    public ZidiumEvent getEvent(String message, Throwable exception) {
        ZidiumEvent event = new ZidiumEvent();
        if (message!=null){
             event.setMessage(message);
        } 
        else{
             event.setMessage(exception.getMessage());
        }
        event.setImportance(EventImportance.ALARM);
        setType(event, exception);
        event.setDate(_client.getTimeService().getNow());
        return event;
    }    
}
