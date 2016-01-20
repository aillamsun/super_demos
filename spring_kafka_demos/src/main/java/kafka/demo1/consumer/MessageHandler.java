package kafka.demo1.consumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Created by sungang on 2016/1/11.16:10
 */
public class MessageHandler implements Runnable{
    private KafkaStream<byte[], byte[]> stream;

    public MessageHandler(KafkaStream<byte[], byte[]> s) {
        stream = s;
    }

    @Override
    public void run() {
        try {
            ConsumerIterator<byte[], byte[]> consumerIte = stream.iterator();
            while (consumerIte.hasNext()) {
                System.out.println("Message: " + new String(consumerIte.next().message()));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
