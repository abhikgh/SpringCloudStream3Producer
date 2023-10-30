package com.example.SpringCloudStream3Producer;

import com.example.SpringCloudStream3Producer.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.messaging.Sink;
import reactor.core.publisher.Sinks;

@SpringBootApplication
public class SpringCloudStream3ProducerApplication implements CommandLineRunner {

	@Autowired
	private Sinks.Many<Book> sink;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStream3ProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Book book = new Book(1, "attwn");
		sink.tryEmitNext(book);
		System.out.println("Book emitted successfully...");
	}
}
