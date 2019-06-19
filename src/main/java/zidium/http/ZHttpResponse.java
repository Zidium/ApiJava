package zidium.http;

import java.io.UnsupportedEncodingException;


public class ZHttpResponse {
    
    public int Code;
    public byte [] Bytes;
    
    public String getStringUTF8() throws UnsupportedEncodingException, Exception{
        validateCode();
        String result = new String(Bytes, "UTF-8");
        result = result.replace("\uFEFF", ""); // Иногда может попадаться BOM в начале потока
        return result;
    }
    
    public void validateCode() throws Exception{
        if (Code!=200){
            throw new Exception("Response code " + Code);
        }
    }
}
