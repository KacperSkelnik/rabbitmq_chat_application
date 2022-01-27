
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Send implements Runnable {
    private static final String EXCHANGE_NAME = "amq.fanout";
    private Connection connection;
    private Channel channel;

    Send() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        System.out.println("Nick:");
        String nick = input.nextLine();
        while(true) {
            String message = nick + ": " + input.nextLine();
            try {
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            } catch (IOException e) {
                input.close();
                e.printStackTrace();
            }
        }
        
    }
}