package com.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableBinding(Binding.class)
@RestController
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	private MessageChannel channel;

	public ProducerApplication(Binding binding) {
		this.channel = binding.outputBinding();
	}

	@GetMapping("/send/{name}")
	public void sendMessage(@PathVariable String name) {
		Message<String> message = MessageBuilder.withPayload("Hello " + name).build();
		try {
			channel.send(message);
			System.out.println("Sent message to Kafka...");
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
	}

}

interface Binding {

	String OUTPUT = "output";

	@Output(OUTPUT)
	MessageChannel outputBinding();

}