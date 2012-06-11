package com.sillycat.magicneptune.example;

import java.util.Properties;

import kafka.api.FetchRequest;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.javaapi.producer.ProducerData;
import kafka.javaapi.producer.Producer;
import kafka.message.MessageAndOffset;
import kafka.producer.ProducerConfig;

public class TestMain {

	public static void main(String[] args) {
		Properties props = new Properties();
		//props.put("zk.connect", "ud1129.chinaw3.com:2181");
		//localhost:2182/kafka
		props.put("zk.connect", "ud1129.chinaw3.com:2181");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// This is added by myself for changing the default timeout 6000.
		props.put("connect.timeout.ms", "15000");
		props.put("socket.timeout.ms", "15000");
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);

		// The message is sent to a randomly selected partition registered in ZK
		ProducerData<String, String> data = new ProducerData<String, String>(
				"test", "test-message");
		producer.send(data);
		producer.close();

		SimpleConsumer consumer = new SimpleConsumer("ud1129.chinaw3.com", 9092, 10000,
				1024000);

		long offset = 0;
		while (true) {
			// create a fetch request for topic ¡°test¡±, partition 0, current
			// offset, and fetch size of 1MB
			FetchRequest fetchRequest = new FetchRequest("test", 0, offset,
					1000000);

			// get the message set from the consumer and print them out
			ByteBufferMessageSet messages = consumer.fetch(fetchRequest);
			for (MessageAndOffset msg : messages) {
				System.out.println(ExampleUtils.getMessage(msg.message()));
				// advance the offset after consuming each message
				offset = msg.offset();
			}
		}

	}

}
