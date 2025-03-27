package wales.nhs.dhcw.inthub.sample.sbcon;

import wales.nhs.dhcw.inthub.sample.sbcon.lib.Config;
import wales.nhs.dhcw.inthub.sample.sbcon.lib.LoggingReceiver;

public class TranslationDlqReceiver {
    public static void main(String[] args) throws Exception {

        Config config = Config.readConfig();
        String queueName = "dhcw-integration-hub-poc-docker-translation";

        var loggingReceiver = LoggingReceiver.createDlqQueueReceiver(
                config.connectionString(), queueName
        );

        loggingReceiver.receiveMessages();
    }
}
