package zidium.dto;

public abstract class Response {
    
    public int Code;
    public String ErrorMessage;
    
    public boolean success(){
        return Code == 10;
    }  
}
