package zidium.dto;

import java.util.ArrayList;
import zidium.helpers.ByteHelper;
import zidium.dto.ExtentionPropertyDto;


public class ExtentionPropertyCollection {
    
    private ArrayList<ExtentionPropertyDto> _properties = new ArrayList<>();
    
    public ExtentionPropertyDto[] getAll(){
        return _properties.toArray(new ExtentionPropertyDto[_properties.size()]);
    }
    
    private void addProperty(String name, String value, String type){
        ExtentionPropertyDto property = new ExtentionPropertyDto();
        property.Name = name;
        property.Value = value;
        property.Type = type;
        _properties.add(property);
    }
    
    public void addString(String name, String value){
        addProperty(name, value, "String");
    }
    
    public void addInteger(String name, Integer value){
        String text = null;
        if (value!=null){
            text = value.toString();
        }
        addProperty(name, text, "String");
    }
    
    public void addBinary(String name, byte[] bytes){
        String value = ByteHelper.toBase64(bytes);
        addProperty(name, value, "Binary");
    }
}
