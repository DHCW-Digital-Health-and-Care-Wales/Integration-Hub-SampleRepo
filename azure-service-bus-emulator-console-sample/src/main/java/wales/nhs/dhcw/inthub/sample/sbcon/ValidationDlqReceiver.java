package wales.nhs.dhcw.inthub.sample.sbcon;

import wales.nhs.dhcw.inthub.sample.sbcon.lib.Config;
import wales.nhs.dhcw.inthub.sample.sbcon.lib.LoggingReceiver;

public class ValidationDlqReceiver {
    public static void main(String[] args) throws Exception {

        Config config = Config.readConfig();
        String queueName = "dhcw-integration-hub-poc-docker-ingress";

        var loggingReceiver = LoggingReceiver.createDlqQueueReceiver(
                config.connectionString(), queueName
        );

        loggingReceiver.receiveMessages();
    }
}
