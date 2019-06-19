package zidium.webServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GsonSerializer implements ISerializer{

    private final Gson _gson;
    
    public GsonSerializer(){
        _gson = getGson();
    }
    
    private Gson getGson(){
       
        // UTC
        TimeZone tz = TimeZone.getTimeZone("UTC");
        
        // ms
        SimpleDateFormat formatMs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatMs.setTimeZone(tz);
        
        // sec
        SimpleDateFormat formatSec = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formatSec.setTimeZone(tz);
        
        // serialize
        JsonSerializer<Date> ser = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(
                Date src, 
                Type typeOfSrc, 
                JsonSerializationContext context) {
                if (src==null){
                    return null;
                }
                String text = formatMs.format(src);
                return new JsonPrimitive(text);
            }     
        };

        // deserialize
        JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(
                JsonElement json, 
                Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
                if (json==null){
                    return null;
                }
                try{
                    String text = json.getAsString();
                    if (text.length()==24){ // 2016-04-30T23:25:43.511Z
                        return formatMs.parse(text);
                    }
                    if (text.length()==20){
                        return formatSec.parse(text);
                    }
                    throw new JsonParseException("invalid date format");
                }
                catch(ParseException e){
                    throw new JsonParseException("invalid date format");
                }
            }
        };
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, ser)
                .registerTypeAdapter(Date.class, deser)
                .create();
    }
    
    @Override
    public String toString(Object object) {
        return _gson.toJson(object);
    }

    @Override
    public Object fromString(String text, Class cl) {  
        return _gson.fromJson(text, cl);
    }    
}
