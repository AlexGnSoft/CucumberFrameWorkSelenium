package helpfiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
    public static Properties properties;

    public PropertiesFile(){
        BufferedReader reader;
        String propertyFilePath = "src/main/java/helpfiles/config.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getBrowser() {
        String browser = properties.getProperty("browser");
        if(browser != null) return browser;
        else throw new RuntimeException("browser is not specified in the config.properties file");
    }


    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if(url != null) return url;
        else throw new RuntimeException("url is not specified in the config.properties file");
    }

    public String getDriverPath_Chrome() {
        String driverPath_Chrome = properties.getProperty("driverPath_Chrome");
        if(driverPath_Chrome != null) return driverPath_Chrome;
        else throw new RuntimeException("driverPath for Chrome is not specified in the config.properties file");
    }

    public String getDriverPath_FireFox() {
        String driverPath_FireFox = properties.getProperty("driverPath_FireFox");
        if(driverPath_FireFox != null) return driverPath_FireFox;
        else throw new RuntimeException("driverPath for FireFox is not specified in the config.properties file");
    }
}
