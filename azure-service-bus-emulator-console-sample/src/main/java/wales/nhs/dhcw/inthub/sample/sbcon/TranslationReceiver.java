package wales.nhs.dhcw.inthub.sample.sbcon;

import wales.nhs.dhcw.inthub.sample.sbcon.lib.Config;
import wales.nhs.dhcw.inthub.sample.sbcon.lib.LoggingReceiver;

public class TranslationReceiver {

    public static void main(String[] args) throws Exception {
        Config config = Config.readConfig();
        String queueName = "dhcw-integration-hub-poc-docker-sender";

        var loggingReceiver = LoggingReceiver.createQueueReceiver(
                config.connectionString(), queueName
        );

        loggingReceiver.receiveMessages();
    }
}
