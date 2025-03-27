package wales.nhs.dhcw.inthub.sample.sbcon.lib;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final Properties configProperties;

    public Config(Properties configProperties) {
        this.configProperties = configProperties;
    }

    public static Config readConfig() throws IOException {
        String envConfig = Thread.currentThread().getContextClassLoader().getResource("env.properties").getPath();
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(envConfig));
        return new Config(appProps);
    }

    public String connectionString() {
        return configProperties.getProperty("CONNECTION_STRING");
    }

    public String ingressQueueName() {
        return configProperties.getProperty("INGRESS_QUEUE_NAME");
    }
}
