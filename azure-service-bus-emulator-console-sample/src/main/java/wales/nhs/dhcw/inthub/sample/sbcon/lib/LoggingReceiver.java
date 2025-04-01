package wales.nhs.dhcw.inthub.sample.sbcon.lib;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.models.SubQueue;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class LoggingReceiver {

    private final ServiceBusReceiverClient receiverClient;
    private final String channelName;

    public LoggingReceiver(ServiceBusReceiverClient receiverClient, String channelName) {
        this.receiverClient = receiverClient;
        this.channelName = channelName;
    }

    public static LoggingReceiver createTopicSubscriptionReceiver(String connectionString, String topicName, String subscriptionName) {
        ServiceBusClientBuilder builder = new ServiceBusClientBuilder()
                .connectionString(connectionString);
        ServiceBusReceiverClient receiverClient = builder
                .receiver()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .buildClient();
        return new LoggingReceiver(receiverClient, topicName + " : " + subscriptionName);
    }

    public static LoggingReceiver createQueueReceiver(String connectionString, String queueName) {
        ServiceBusClientBuilder builder = new ServiceBusClientBuilder()
                .connectionString(connectionString);
        ServiceBusReceiverClient receiverClient = builder
                .receiver()
                .queueName(queueName)
                .buildClient();
        return new LoggingReceiver(receiverClient, queueName);
    }

    public static LoggingReceiver createDlqQueueReceiver(String connectionString, String queueName) {
        ServiceBusClientBuilder builder = new ServiceBusClientBuilder()
                .connectionString(connectionString);
        ServiceBusReceiverClient receiverClient = builder
                .receiver()
                .queueName(queueName)
                .subQueue(SubQueue.DEAD_LETTER_QUEUE)
                .buildClient();
        return new LoggingReceiver(receiverClient, queueName + " : DLQ");
    }

    public void receiveMessages() {
        while (true){
            System.out.println("Staring receiving on " + channelName );
            receiverClient.receiveMessages(1).forEach(msg -> {
                try {
                    System.out.println("Received: " + new String(msg.getBody().toBytes(), "UTF-8"));
                    System.out.println("with properties: " + strigifyMap(msg.getApplicationProperties()));
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

    private String strigifyMap(Map<String, Object> properties) {
        var builder = new StringBuilder();
        properties.forEach((key, value) -> {
            builder.append("[").append(key).append("]: ")
                    .append(value).append(";").append("\n");
        });
        return builder.toString();
    }
}
