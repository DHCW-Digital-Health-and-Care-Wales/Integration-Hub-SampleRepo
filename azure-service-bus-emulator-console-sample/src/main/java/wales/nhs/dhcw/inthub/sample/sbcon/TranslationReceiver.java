package wales.nhs.dhcw.inthub.sample.sbcon;

import wales.nhs.dhcw.inthub.sample.sbcon.lib.Config;
import wales.nhs.dhcw.inthub.sample.sbcon.lib.LoggingReceiver;

public class TranslationReceiver {

    public static void main(String[] args) throws Exception {
        Config config = Config.readConfig();
        String topicName = "dhcw-integration-hub-poc-docker-hl7senders";
        String subscriptionName = "dhcw-integration-hub-poc-docker-hl7senders-sub";

        var loggingReceiver = LoggingReceiver.createTopicSubscriptionReceiver(
                config.connectionString(), topicName, subscriptionName
        );

        loggingReceiver.receiveMessages();
    }
}
