package kafka.demo1.producer;

import kafka.javaapi.producer.Producer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.util.Random;

public class SpringKafkaProducer {

    private static final String CONFIG = "/spring/spring-integration-kafka-producer-config.xml";
    private static Random rand = new Random();

    public static void main(String[] args) {
        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG, Producer.class);
        ctx.start();
        final MessageChannel channel = ctx.getBean("inputToKafka", MessageChannel.class);
        for (int i = 0; i < 5; i++) {

            channel.send(MessageBuilder.withPayload("Message-" + rand.nextInt())//设置有效载荷
                    .setHeader("messageKey", String.valueOf(i)) //指定key
                    .setHeader("topic", "lxw1234").build());//指定topic

            System.out.println(MessageBuilder.withPayload("Message-" + rand.nextInt())
                    .setHeader("messageKey", String.valueOf(i))
                    .setHeader("topic", "lxw1234").build().toString());
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}
