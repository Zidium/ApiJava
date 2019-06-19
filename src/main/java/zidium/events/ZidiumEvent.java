package zidium.events;

import zidium.components.IComponentControl;
import java.util.Date;
import zidium.dto.ExtentionPropertyCollection;


public class ZidiumEvent {
    
    private IComponentControl _componentControl;
    private Date _date;
    private String _message;
    private String _importance;
    private String _typeCode;
    private String _typeSystemName;
    private String _typeDisplayName;
    private final ExtentionPropertyCollection _properties = new ExtentionPropertyCollection();
    
    public IComponentControl getComponentControl(){
        return _componentControl;
    }
    
    public void setComponentControl(IComponentControl componentControl){
        _componentControl = componentControl;
    }
    
    public String getMessage(){
        return _message;
    }
    
    public void setMessage(String message){
        _message = message;
    }
    
    public ExtentionPropertyCollection getPropertis(){
        return _properties;
    }
    
    public Date getDate(){
        return _date;
    }
    
    public void setDate(Date date){
        _date = date;
    }
    
    public String getTypeCode(){
        return _typeCode;
    }
    
    public void setTypeCode(String typeCode){
        _typeCode = typeCode;
    }
    
    public String getImportance(){
        return _importance;
    }
    
    public void setImportance(String importance){
        _importance = importance;
    }
    
    public String getTypeSystemName(){
        return _typeSystemName;
    }
    
    public void setTypeSystemName(String typeSystemName){
        _typeSystemName = typeSystemName;
    }
    
    public String getTypeDisplayName(){
        return _typeDisplayName;
    }
    
    public void setTypeDisplayName(String typeDisplayName){
        _typeDisplayName = typeDisplayName;
    }
}
