package zidium.http;

import java.util.ArrayList;
import java.util.List;


public class ZHttpRequest {
    
    public String Method = "GET";
    public String Url;
    public byte[] Data;
    
    private ArrayList<ZHttpRequestProperty> _properties = new ArrayList<ZHttpRequestProperty>();
    
    public void addProperty(String name, String value){
        ZHttpRequestProperty property = new ZHttpRequestProperty();
        property.Name = name;
        property.Value = value;
        _properties.add(property);
    }
    
    public ZHttpRequestProperty[] getProperties(){
        return _properties.toArray(new ZHttpRequestProperty[_properties.size()]);
    }
}
