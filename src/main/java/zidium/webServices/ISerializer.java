package zidium.webServices;

public interface ISerializer {
    
    public String toString(Object object);
    public Object fromString(String text, Class cl);
    
}
