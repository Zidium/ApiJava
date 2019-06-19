package zidium.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionHelper {
    
    public static String getStackTraceAsString(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }    
}
