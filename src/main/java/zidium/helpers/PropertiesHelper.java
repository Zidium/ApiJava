package zidium.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    private final Properties _properties;

    public PropertiesHelper(Properties properties) {
        _properties = properties;
    }

    public String getRequiredString(String key) throws Exception {
        String value = getString(key);
        if (value == null) {
            throw new Exception("property value not found: " + key);
        }
        if ("".equals(value)) {
            throw new Exception("empty property value: " + key);
        }
        return value;
    }
    
    public String getString(String key) throws Exception {
        String value = _properties.getProperty(key);
        return value;
    }

    public int getInt(String key) throws Exception {
        String value = getRequiredString(key);
        return Integer.parseInt(value);
    }

    public static PropertiesHelper fromFile(String file) throws IOException {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(file)) {
            prop.load(input);
        }
        return new PropertiesHelper(prop);
    }
}
