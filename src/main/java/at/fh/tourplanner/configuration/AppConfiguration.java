package at.fh.tourplanner.configuration;

import lombok.Builder;
import lombok.Data;

import java.util.Locale;
import java.util.Properties;
@Data
@Builder
public class AppConfiguration {
        private String datasourceUrl;
        private String datasourceUsername;
        private String datasourcePassword;
        private String apiKey;

        public static AppConfiguration fromProperties(Properties appProps) {
            return AppConfiguration.builder()
                    .datasourceUrl(appProps.getProperty("datasource.url"))
                    .datasourceUsername(appProps.getProperty("datasource.user"))
                    .datasourcePassword(appProps.getProperty("datasource.pw"))
                    .apiKey(appProps.getProperty("apikey"))
                    .build();
        }
}
