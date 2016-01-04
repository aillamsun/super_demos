package kafka.demo1.main;


import kafka.config.KafkaProperties;
import kafka.demo1.consumer.KafkaConsumer;
import kafka.demo1.producer.KafkaProducer;

public class KafkaConsumerProducerDemo {

	public static void main(String[] args) {
		KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
		producerThread.start();
		KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
		consumerThread.start();
	}
}
