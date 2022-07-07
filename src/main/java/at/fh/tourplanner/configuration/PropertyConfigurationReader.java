package at.fh.tourplanner.configuration;

import lombok.extern.log4j.Log4j2;

import java.util.Properties;
@Log4j2
public class PropertyConfigurationReader implements AppConfigurationReader {
    private final Properties appProps = new Properties();
    private boolean initialized = false;
    @Override
    public AppConfiguration getAppConfiguration() {
        if (!initialized) {
            try {
                log.info("Reading properties from file");
                appProps.load(Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("app.properties"));
            } catch (Exception e) {
                log.error("Failed to load app.properties, please make sure there is a file 'app.properties' available", e);
                System.exit(1);
            }
            initialized = true;
        }

        return AppConfiguration.fromProperties(appProps);
    }
}
