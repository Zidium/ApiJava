
package zidium.helpers;

import java.util.UUID;


public class GuidHelper {
    
    public static String getRandom(){
        return UUID.randomUUID().toString();
    }
}
