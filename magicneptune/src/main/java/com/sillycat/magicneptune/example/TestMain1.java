package com.sillycat.magicneptune.example;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import kafka.api.FetchRequest;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.javaapi.producer.Producer;
import kafka.javaapi.producer.ProducerData;
import kafka.message.MessageAndOffset;
import kafka.producer.ProducerConfig;

public class TestMain1 {

	public static void main(String[] args) {
		
		
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		SimpleConsumer consumer = new SimpleConsumer("ud1129.chinaw3.com", 9092, 10000,
				1024000);

		long offset = 0;
		while (true) {
			// create a fetch request for topic ��test��, partition 0, current
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
