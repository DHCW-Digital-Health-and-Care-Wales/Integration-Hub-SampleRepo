package wales.nhs.dhcw.inthub.sample.sbcon;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.models.SubQueue;

import java.io.UnsupportedEncodingException;

public class DlqReceiver {
    private static final String CONNECTION_STRING = "Endpoint=sb://127.0.0.1;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=SAS_KEY_VALUE;UseDevelopmentEmulator=true;" ;

    public static void main(String[] args) {
        // Create a ServiceBusClientBuilder
        ServiceBusClientBuilder builder = new ServiceBusClientBuilder()
            .connectionString(CONNECTION_STRING);

        // Receiving a message
        ServiceBusReceiverClient receiverClient = builder
            .receiver()
            .queueName("dhcw-integration-hub-poc-docker-ingress")
            .subQueue(SubQueue.DEAD_LETTER_QUEUE)
            .buildClient();

        while (true){
            receiverClient.receiveMessages(1).forEach(msg -> {
                try {
                    System.out.println("Received: " + new String(msg.getBody().toBytes(), "UTF-16"));
                    System.out.println("Reason: " + msg.getDeadLetterReason());
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
