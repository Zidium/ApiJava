package zidium.helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class StackHelper {

    public static String StackToString(Exception exception) {
        try {
            try (StringWriter stringWriter = new StringWriter()) {
                try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
                    exception.printStackTrace(printWriter);
                    return stringWriter.toString();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }
    
    public static String toString(StackTraceElement[] stackTraceElements){
        if (stackTraceElements==null){
            return "stack is null";
        }
        if (stackTraceElements.length==0){
            return "stack length is zero";
        }
        String result = "";
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            result += stackTraceElement.toString() + System.lineSeparator();
        }
        return result;
    }
}
