package wales.nhs.dhcw.inthub.sample.sbcon;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;

import java.io.UnsupportedEncodingException;

public class TranslatedReceiver {
    private static final String CONNECTION_STRING = "Endpoint=sb://127.0.0.1;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=SAS_KEY_VALUE;UseDevelopmentEmulator=true;" ;

    public static void main(String[] args) {
        // Create a ServiceBusClientBuilder
        ServiceBusClientBuilder builder = new ServiceBusClientBuilder()
            .connectionString(CONNECTION_STRING);

        // Receiving a message
        ServiceBusReceiverClient receiverClient = builder
            .receiver()
            .topicName("dhcw-integration-hub-poc-docker-hl7senders")
            .subscriptionName("dhcw-integration-hub-poc-docker-hl7senders-sub")
            .buildClient();

        while (true){
            receiverClient.receiveMessages(1).forEach(msg -> {
                try {
                    System.out.println("Received: " + new String(msg.getBody().toBytes(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                receiverClient.complete(msg);
            });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
